package app.cancel;

import app.AppException;
import app.cancel.CancelRoomControl;

public class CancelRoomForm {
    private CancelRoomControl CancelRoomControl = new CancelRoomControl();
   private String roomNumber;

   public CancelRoomForm() {
   }

   private CancelRoomControl getCancelRoomControl() {
      return this.CancelRoomControl;
   }

   public void cancel() throws AppException {
      CancelRoomControl var1 = this.getCancelRoomControl();
      var1.cancel(this.roomNumber);
   }

   public String getRoomNumber() {
      return this.roomNumber;
   }

   public void setRoomNumber(String var1) {
      this.roomNumber = var1;
   }
}
