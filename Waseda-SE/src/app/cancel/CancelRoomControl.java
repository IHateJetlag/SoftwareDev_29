package app.cancel;

import java.util.Date;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentManager;
import domain.payment.PaymentException;
import domain.room.RoomManager;
import domain.room.RoomException;
//
public class CancelRoomControl {
    	   public void cancel(String reservationNumber) throws AppException {
			   try {
					   // Clear room and get staying date
					   RoomManager roomManager = getRoomManager();
					   Date stayingDate = roomManager.removeCustomer(reservationNumber);

					   // Consume payment
					   PaymentManager paymentManager = getPaymentManager();
					   paymentManager.consumePayment(stayingDate, reservationNumber);
			   }
			   catch (RoomException e) {
					   AppException exception = new AppException("Failed to cancel", e);
					   exception.getDetailMessages().add(e.getMessage());
					   exception.getDetailMessages().addAll(e.getDetailMessages());
					   throw exception;
			   }
			   catch (PaymentException e) {
					   AppException exception = new AppException("Failed to cancel", e);
					   exception.getDetailMessages().add(e.getMessage());
					   exception.getDetailMessages().addAll(e.getDetailMessages());
					   throw exception;
			   }
	   }

	private RoomManager getRoomManager() {



		return ManagerFactory.getInstance().getRoomManager();
	}

	private PaymentManager getPaymentManager() {
		return ManagerFactory.getInstance().getPaymentManager();
	}
}
