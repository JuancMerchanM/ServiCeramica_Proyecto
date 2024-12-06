package Model;

public class TransferPayment extends Payment {

    private String senderNumber;
    private String platform;

    public TransferPayment(double amount, String senderNumber, String platform) {
        super(amount);
        this.senderNumber = senderNumber;
        this.platform = platform;
        this.setType("Transfer");
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String getPaymentDetails() {
        return "Pago por transferencia: Monto total = " + getAmount() +
                ", Número remitente = " + senderNumber +
                ", Plataforma = " + platform;
    }
}
