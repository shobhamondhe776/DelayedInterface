package Interface;

import java.time.Instant;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Coupon implements Delayed {
    private final String code;
    private final Instant expirationTime;

    public Coupon(String code, long expirationTimeInSeconds) {
        this.code = code;
        this.expirationTime = Instant.now().plusSeconds(expirationTimeInSeconds);
    }

    public String getCode() {
        return code;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remainingTime = expirationTime.getEpochSecond() - Instant.now().getEpochSecond();
        return unit.convert(remainingTime, TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }

        if (other instanceof Coupon) {
            Coupon otherCoupon = (Coupon) other;
            return Long.compare(this.expirationTime.getEpochSecond(), otherCoupon.expirationTime.getEpochSecond());
        }

        long diff = getDelay(TimeUnit.SECONDS) - other.getDelay(TimeUnit.SECONDS);
        return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
    }
}


