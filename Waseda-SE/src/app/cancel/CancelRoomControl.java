package app.cancel;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentManager;
import domain.payment.PaymentException;
// import domain.room.RoomManager; // RoomManagerは不要になるためコメントアウトまたは削除
// import domain.room.RoomException; // RoomExceptionは不要になるためコメントアウトまたは削除
import domain.reservation.ReservationManager; // ReservationManager をインポート
import domain.reservation.ReservationException; // ReservationException をインポート

public class CancelRoomControl {
    public void cancel(String reservationNumber) throws AppException {
        try {
            // 部屋のクリア（チェックアウト）ではなく、予約のキャンセルを行う
            ReservationManager reservationManager = getReservationManager(); // ReservationManager を取得
            Date stayingDate = reservationManager.cancelReservation(reservationNumber); // 予約キャンセルメソッドを呼び出す

        }
        catch (ReservationException e) { // RoomException から ReservationException に変更
            AppException exception = new AppException("Failed to cancel reservation", e); // メッセージを予約向けに
            exception.getDetailMessages().add(e.getMessage());
            exception.getDetailMessages().addAll(e.getDetailMessages());
            throw exception;
        }
    }

    // RoomManager の代わりに ReservationManager を取得するメソッドを追加
    private ReservationManager getReservationManager() {
        return ManagerFactory.getInstance().getReservationManager();
    }
}
