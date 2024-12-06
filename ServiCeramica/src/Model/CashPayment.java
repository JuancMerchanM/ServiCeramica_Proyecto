package Model;

public class CashPayment extends Payment {
    private double cashReceived;

    public CashPayment(double amount, double cashReceived) {
        super(amount);
        this.cashReceived = cashReceived;
        this.setType("Cash");
    }

    public double getCashReceived() {
        return cashReceived;
    }

    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }

    @Override
    public String getPaymentDetails() {
        return "Pago en efectivo: Monto total = " + getAmount() +
                ", Dinero recibido = " + cashReceived +
                ", Cambio = " + (cashReceived - getAmount());
    }
}
