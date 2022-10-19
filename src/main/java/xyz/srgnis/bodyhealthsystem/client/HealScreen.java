package xyz.srgnis.bodyhealthsystem.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.srgnis.bodyhealthsystem.body.Body;
import xyz.srgnis.bodyhealthsystem.body.BodyPart;
import xyz.srgnis.bodyhealthsystem.body.player.BodyProvider;
import xyz.srgnis.bodyhealthsystem.body.player.PlayerBodyParts;
import xyz.srgnis.bodyhealthsystem.network.ClientNetworking;

public class HealScreen extends HandledScreen<ScreenHandler> {
    public static final Text HEAD = Text.literal("HEAD");
    public static final Text LEFT_ARM = Text.literal("LEFT ARM");
    public static final Text RIGHT_ARM = Text.literal("RIGHT ARM");
    public static final Text TORSO = Text.literal("TORSO");
    public static final Text LEFT_LEG = Text.literal("LEFT LEG");
    public static final Text RIGHT_LEG = Text.literal("RIGHT LEG");
    public static final Text LEFT_FOOT = Text.literal("LEFT FOOT");
    public static final Text RIGHT_FOOT = Text.literal("RIGHT FOOT");
    private final PlayerEntity player;

    private final Body body;
    private final int buttonWidth = 75;
    private final int buttonHeight = 20;
    private final int buttonMargin = 2;
    private final int total = (buttonHeight+buttonMargin)*5;

    private final int heightAndMargin = buttonHeight+buttonMargin;
    private final int widthAndMargin = buttonWidth+buttonMargin;

    BodyPartHealButton headButton;
    BodyPartHealButton leftArmButton;
    BodyPartHealButton rightArmButton;
    BodyPartHealButton torsoButton;
    BodyPartHealButton leftLegButton;
    BodyPartHealButton rightLegButton;
    BodyPartHealButton leftFootButton;
    BodyPartHealButton rightFootButton;

    public HealScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.body = ((BodyProvider)inventory.player).getBody();
        this.player = inventory.player;

        headButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.HEAD),0, 0, buttonWidth, buttonHeight, HEAD);
        leftArmButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.LEFT_ARM),0, 0, buttonWidth, buttonHeight, LEFT_ARM);
        rightArmButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.RIGHT_ARM),0, 0, buttonWidth, buttonHeight, RIGHT_ARM);
        torsoButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.TORSO),0, 0, buttonWidth, buttonHeight, TORSO);
        leftLegButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.LEFT_LEG),0, 0, buttonWidth, buttonHeight, LEFT_LEG);
        rightLegButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.RIGHT_LEG),0, 0, buttonWidth, buttonHeight, RIGHT_LEG);
        leftFootButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.LEFT_FOOT),0, 0, buttonWidth, buttonHeight, LEFT_FOOT);
        rightFootButton = new BodyPartHealButton(body.getPart(PlayerBodyParts.RIGHT_FOOT),0, 0, buttonWidth, buttonHeight, RIGHT_FOOT);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int row = 0;
        int startX = (this.width - this.backgroundWidth) / 2;
        int startY = (this.height - this.backgroundHeight) / 2;
        int buttonStartY = startY+((this.backgroundHeight - total) / 2);
        int centerX = ((this.backgroundWidth - buttonWidth) / 2) + startX;
        int centerLeftX = ((this.backgroundWidth - buttonWidth * 2) / 2) + startX;

        fill(matrices, startX, startY, startX+this.backgroundWidth, startY+this.backgroundHeight, 0xff999999);

        headButton.x=centerX;
        headButton.y=buttonStartY+((heightAndMargin)*row);
        headButton.checkAndSetActive();
        this.addDrawableChild(headButton);
        row++;

        leftArmButton.x=centerLeftX;
        leftArmButton.y=buttonStartY+((heightAndMargin)*row);
        leftArmButton.checkAndSetActive();
        this.addDrawableChild(leftArmButton);

        rightArmButton.x=centerLeftX+widthAndMargin;
        rightArmButton.y=buttonStartY+((heightAndMargin)*row);
        rightArmButton.checkAndSetActive();
        this.addDrawableChild(rightArmButton);
        row++;

        torsoButton.x=centerX;
        torsoButton.y=buttonStartY+((heightAndMargin)*row);
        torsoButton.checkAndSetActive();
        this.addDrawableChild(torsoButton);
        row++;

        leftLegButton.x=centerLeftX;
        leftLegButton.y=buttonStartY+((heightAndMargin)*row);
        leftLegButton.checkAndSetActive();
        this.addDrawableChild(leftLegButton);

        rightLegButton.x=centerLeftX+widthAndMargin;
        rightLegButton.y=buttonStartY+((heightAndMargin)*row);
        rightLegButton.checkAndSetActive();
        this.addDrawableChild(rightLegButton);
        row++;

        leftFootButton.x=centerLeftX;
        leftFootButton.y=buttonStartY+((heightAndMargin)*row);
        leftFootButton.checkAndSetActive();
        this.addDrawableChild(leftFootButton);

        rightFootButton.x=centerLeftX+widthAndMargin;
        rightFootButton.y=buttonStartY+((heightAndMargin)*row);
        rightFootButton.checkAndSetActive();
        this.addDrawableChild(rightFootButton);
        row++;
    }

    class BodyPartHealButton extends ButtonWidget {
        public BodyPart part;
        public BodyPartHealButton(BodyPart part, int x, int y, int width, int height, Text message) {
            super(x, y, width, height, message, button -> {
                ClientNetworking.useHealingItem(part.getIdentifier(), player.getMainHandStack());
                HealScreen.this.close();
            });
            this.part = part;
        }

        public BodyPartHealButton(BodyPart part, int x, int y, int width, int height, Text message, PressAction pressAction) {
            super(x, y, width, height, message, pressAction);
            this.part = part;
        }

        public void checkAndSetActive(){
            active = part.isDamaged();
        }
    }
}
