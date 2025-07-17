package app.cancel;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
// import domain.payment.PaymentManager; // PaymentManagerは不要なため削除
// import domain.payment.PaymentException; // PaymentExceptionは不要なため削除
// import domain.room.RoomManager; // RoomManagerは不要なため削除
// import domain.room.RoomException; // RoomExceptionは不要なため削除
import domain.reservation.ReservationManager; // ReservationManager をインポート
import domain.reservation.ReservationException; // ReservationException をインポート

public class CancelRoomControl {
    public void cancel(String reservationNumber) throws AppException {
        try {
            ReservationManager reservationManager = getReservationManager(); 
            reservationManager.cancelReservation(reservationNumber); 


        }
        catch (ReservationException e) { 
            AppException exception = new AppException("Failed to cancel reservation", e);
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        }
    }
    private ReservationManager getReservationManager() {
        return ManagerFactory.getInstance().getReservationManager();
    }
}
