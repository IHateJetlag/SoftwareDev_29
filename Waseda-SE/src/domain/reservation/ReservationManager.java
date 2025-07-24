/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.reservation;

import java.util.Calendar;
import java.util.Date;

import domain.DaoFactory;
import domain.room.Room;
import domain.room.RoomDao;
import domain.room.RoomException;

/**
 * Manager for reservations<br>
 * 
 */
public class ReservationManager {
	
	public String createReservation(Date stayingDate) throws ReservationException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}

		Reservation reservation = new Reservation();
		String reservationNumber = generateReservationNumber();
		reservation.setReservationNumber(reservationNumber);
		reservation.setStayingDate(stayingDate);
		reservation.setStatus(Reservation.RESERVATION_STATUS_CREATE);

		ReservationDao reservationDao = getReservationDao();
		reservationDao.createReservation(reservation);
		return reservationNumber;
	}

	private synchronized String generateReservationNumber() {
		Calendar calendar = Calendar.getInstance();
		try {
			Thread.sleep(10);
		}
		catch (Exception e) {
		}
		return String.valueOf(calendar.getTimeInMillis());
	}

	public Date consumeReservation(String reservationNumber) throws ReservationException,
			NullPointerException {
		if (reservationNumber == null) {
			throw new NullPointerException("reservationNumber");
		}

		ReservationDao reservationDao = getReservationDao();
		Reservation reservation = reservationDao.getReservation(reservationNumber);
		//If corresponding reservation does not exist
		if (reservation == null) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_NOT_FOUND);
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}
		//If reservation has been consumed already
		if (!reservation.getStatus().equals(Reservation.RESERVATION_STATUS_CREATE)) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_ALREADY_CONSUMED);
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}

		Date stayingDate = reservation.getStayingDate();
		reservation.setStatus(Reservation.RESERVATION_STATUS_CONSUME);
		reservationDao.updateReservation(reservation);
		return stayingDate;
	}	

	public Date cancelReservation(String reservationNumber) throws ReservationException, NullPointerException {
		if (reservationNumber == null) {
			throw new NullPointerException("reservationNumber");
		}

		ReservationDao reservationDao = getReservationDao(); //
		Reservation reservation = reservationDao.getReservation(reservationNumber); //

		// 該当する予約が存在しない場合
		if (reservation == null) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_NOT_FOUND); //
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}

		// 予約が既にキャンセル済み、または消費済みの場合
		// consumeReservationのロジックも参考に、既に処理済みの予約はキャンセルできないようにする
		if (reservation.getStatus().equals(ReservationException.RESERVATION_STATUS_CANCELED) ||
		    reservation.getStatus().equals(Reservation.RESERVATION_STATUS_CONSUME)) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_ALREADY_CANCELED); // 新しいエラーコードを使う
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}

		Date stayingDate = reservation.getStayingDate(); //
		reservation.setStatus(ReservationException.RESERVATION_STATUS_CANCELED); // ステータスをキャンセル済みに変更
		reservationDao.updateReservation(reservation); //

		return stayingDate; // キャンセルされた予約の宿泊日を返す
	}

	private ReservationDao getReservationDao() {
		return DaoFactory.getInstance().getReservationDao();
	}
}