package net.thedudemc.endure.item.attributes;

import com.google.gson.annotations.Expose;
import org.bukkit.ChatColor;
import org.bukkit.attribute.AttributeModifier;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EndureModifier {

    @Expose private final double amount;
    @Expose private final AttributeModifier.Operation operation;
    @Expose private final String name;
    @Expose private final UUID id;
    @Expose private final ChatColor amountColor;

    public EndureModifier(String nameIn, double amountIn, AttributeModifier.Operation operationIn) {
        this(getRandomUUID(ThreadLocalRandom.current()), nameIn, amountIn, ChatColor.YELLOW, operationIn);
    }

    public EndureModifier(UUID uuid, String nameIn, double amountIn, ChatColor amountColor, AttributeModifier.Operation operationIn) {
        this.id = uuid;
        this.name = nameIn;
        this.amount = amountIn;
        this.operation = operationIn;
        this.amountColor = amountColor;
    }

    public EndureModifier(String nameIn, double amountIn, ChatColor amountColor, AttributeModifier.Operation operationIn) {
        this(getRandomUUID(ThreadLocalRandom.current()), nameIn, amountIn, amountColor, operationIn);
    }

    public AttributeModifier getAttributeModifier() {
        return new AttributeModifier(this.id, this.name, this.amount, this.operation);
    }

    public UUID getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public AttributeModifier.Operation getOperation() {
        return this.operation;
    }

    public ChatColor getAmountColor() {
        return this.amountColor;
    }

    public String getAmountString() {
        NumberFormat nf = new DecimalFormat("##.##");
        return "" + this.getAmountColor() + nf.format(this.amount) + ChatColor.RESET;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            EndureModifier attributemodifier = (EndureModifier) p_equals_1_;
            return Objects.equals(this.id, attributemodifier.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + (String) this.name + '\'' + ", id=" + this.id + '}';
    }

    public static UUID getRandomUUID(Random rand) {
        long i = rand.nextLong() & -61441L | 16384L;
        long j = rand.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(i, j);
    }
}
