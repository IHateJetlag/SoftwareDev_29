package app.cancel;

import app.AppException;
import app.cancel.CancelRoomControl;
//


public class CancelRoomForm {
    private CancelRoomControl CancelRoomControl = new CancelRoomControl();
   private String reservationNumber;

   public CancelRoomForm() {
   }

   private CancelRoomControl getCancelRoomControl() {
      return this.CancelRoomControl;
   }

   public void cancel() throws AppException {
      CancelRoomControl var1 = this.getCancelRoomControl();
      var1.cancel(this.reservationNumber);
   }

   public String getreservationNumber() {
      return this.reservationNumber;
   }

   public void setreservationNumber(String var1) {
      this.reservationNumber = var1;
   }
}
