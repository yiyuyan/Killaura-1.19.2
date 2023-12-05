package cn.ksmcbrigade.KI;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import static net.minecraft.client.Options.genericValueLabel;

public class KillauraConfigGUI extends Screen {

    private OptionsList optionsList;

    private final OptionInstance<Boolean> EnableInServer = OptionInstance.createBoolean("gui.ki.killaura_config_enabled",false,(p_232037_) -> {
        Killaura.config.setEnabledInServer(p_232037_);
    });

    private final OptionInstance<Boolean> SwingHand = OptionInstance.createBoolean("gui.ki.killaura_config_swing_hand",false,(p_168280_) -> {
        Killaura.config.setSwingHand(p_168280_);
    });

    private final OptionInstance<Integer> Reach = new OptionInstance<>("gui.ki.killaura_config_reach",OptionInstance.noTooltip(),(p_231913_, p_231914_) -> {
        return genericValueLabel(p_231913_,Component.translatable("gui.ki.killaura_config_reach_text",p_231914_));
    }, new OptionInstance.IntRange(0,100),12,(p_231992_) -> {
        Killaura.config.setReach((int)p_231992_);
    });

    public KillauraConfigGUI() {
        super(Component.nullToEmpty(I18n.get("key.ki.killaura_config")));
    }

    @Override
    protected void init() {

        this.optionsList = new OptionsList(
                this.minecraft, this.width, this.height,
                24,
                this.height - 32,
                25
        );

        this.EnableInServer.set(Killaura.config.isEnabledInServer());
        this.SwingHand.set(Killaura.config.isSwingHand());
        this.Reach.set(Killaura.config.getReach());

        this.optionsList.addBig(this.EnableInServer);
        this.optionsList.addBig(this.SwingHand);
        this.optionsList.addBig(this.Reach);

        this.addWidget(this.optionsList);

        this.addRenderableWidget(new Button((this.width - 200) / 2,this.height - 25,200,20, CommonComponents.GUI_DONE,(p_96057_) -> {
            this.onClose();
        }));
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(poseStack);
        this.optionsList.render(poseStack,mouseX,mouseY,partialTicks);
        drawCenteredString(poseStack, font,this.title.getString(),this.width / 2, 8, 16777215);
        super.render(poseStack,mouseX,mouseY,partialTicks);
    }

    @Override
    public void onClose() {
        Killaura.config.saveToFile();
        super.onClose();
    }

    private static Component percentValueLabel(Component p_231898_, double p_231899_) {
        return Component.translatable("options.percent_value", p_231898_, (int)(p_231899_ * 100.0D));
    }
}
