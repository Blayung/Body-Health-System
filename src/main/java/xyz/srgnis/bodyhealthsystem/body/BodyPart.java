package xyz.srgnis.bodyhealthsystem.body;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import xyz.srgnis.bodyhealthsystem.body.player.PlayerBodyProvider;

//TODO: Allow Override max health on write/read to nbt?
public abstract class BodyPart {
    private float maxHealth;
    private float health;

    protected float criticalThreshold;
    private LivingEntity entity;
    private Identifier identifier;

    protected int armorSlot;

    protected DefaultedList<ItemStack> armorList;
    public BodyPart(float maxHealth, float health, LivingEntity entity, Identifier identifier) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.entity = entity;
        this.identifier = identifier;
        //TODO: add body to parts
    }

    public void heal(){
        setHealth(maxHealth);
    }

    public float heal(float amount){
        float add = health + amount;
        setHealth(Math.min(maxHealth, add));

        return add - health;
    }

    public ItemStack getAffectedArmor(){
        return armorList.get(armorSlot);
    }

    public int getArmorSlot() {
        return armorSlot;
    }

    public void setHealth(float health) {
        this.health = health;
        ((PlayerBodyProvider)entity).getBody().checkNoCritical(this);
    }
    public float getHealth() {
        return health;
    }
    public float getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }
    public LivingEntity getEntity() {
        return entity;
    }
    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }
    public Identifier getIdentifier() {
        return identifier;
    }
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public float getCriticalThreshold() {
        return criticalThreshold;
    }

    public void writeToNbt(NbtCompound nbt){
        NbtCompound new_nbt = new NbtCompound();
        new_nbt.putFloat("health", health);
        nbt.put(identifier.toString(), new_nbt);
    }

    public void readFromNbt(NbtCompound nbt){
        health = nbt.getFloat("health");
    }

    @Override
    public String toString() {
        return identifier + " MaxHP: " + maxHealth + " HP " + health + "\n";
    }
}
