package app.cancel;

import app.AppException;
// CancelRoomControl をそのまま使用

public class CancelRoomForm {
    // フィールド名も既存のまま
    private CancelRoomControl CancelRoomControl = new CancelRoomControl();
    private String reservationNumber; // このフィールドは予約番号を表すのでOK

    public CancelRoomForm() {
    }

    private CancelRoomControl getCancelRoomControl() {
        return this.CancelRoomControl;
    }

    public void cancel() throws AppException {
        CancelRoomControl control = this.getCancelRoomControl(); // 変数名を変更
        control.cancel(this.reservationNumber); // reservationNumberを渡す
    }

    public String getreservationNumber() {
        return this.reservationNumber;
    }

    public void setreservationNumber(String var1) {
        this.reservationNumber = var1;
    }
}