package fengliu.feseliud.data.generator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.item.BaseColorItem;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.utils.color.IColor;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangGeneration extends FabricLanguageProvider {
    private final FabricDataOutput dataGenerator;
    private final Map<String, String> colorTranslations = new HashMap<>();

    public LangGeneration(FabricDataOutput dataGenerator) {
        super(dataGenerator, "zh_cn");
        this.dataGenerator = dataGenerator;
        this.getColorTranslations();
    }

    public void getColorTranslations(){
        this.colorTranslations.put("white", "白色");
        this.colorTranslations.put("orange", "橙色");
        this.colorTranslations.put("magenta", "品红色");
        this.colorTranslations.put("light_blue", "淡蓝色");
        this.colorTranslations.put("yellow", "黄色");
        this.colorTranslations.put("lime", "黄绿色");
        this.colorTranslations.put("pink", "粉红色");
        this.colorTranslations.put("gray", "灰色");
        this.colorTranslations.put("light_gray", "淡灰色");
        this.colorTranslations.put("cyan", "青色");
        this.colorTranslations.put("purple", "紫色");
        this.colorTranslations.put("blue", "蓝色");
        this.colorTranslations.put("brown", "棕色");
        this.colorTranslations.put("green", "绿色");
        this.colorTranslations.put("red", "红色");
        this.colorTranslations.put("black", "黑色");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        JsonObject translations;
        try {
            Path existingFilePath = this.dataGenerator.getModContainer().findPath("assets/" + SummerIceCream.MOD_ID + "/lang/zh_cn.existing.json").get();
            translationBuilder.add(existingFilePath);

            try (Reader reader = Files.newBufferedReader(existingFilePath)) {
                translations = JsonParser.parseReader(reader).getAsJsonObject();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }

        for (Field field : ModItems.class.getDeclaredFields()) {
            Object obj;
            try {
                obj = field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (obj instanceof List<?> items){
                items.forEach(item -> {
                    if (!(item instanceof BaseColorItem colorItem)){
                        return;
                    }

                    String translationKey = "item." + SummerIceCream.MOD_ID + "." + colorItem.getTextureName();
                    try {
                        translationBuilder.add(colorItem, colorItem.setColorTranslation(this.colorTranslations, translationKey, translations));
                    } catch (Exception e) {
                        throw new RuntimeException("未添加键 "+ translationKey + " ?", e);
                    }
                });
            }

            if (obj instanceof Map<?,?> maps){
                maps.forEach((item, levelItem) -> {
                    if(!(item instanceof BaseItem baseItem) || !(levelItem instanceof ILevelItem iLevelItem)){
                        return;
                    }

                    String translationKey = "item." + SummerIceCream.MOD_ID + "." + baseItem.name;
                    try {
                        translationBuilder.add(baseItem, iLevelItem.getTranslations(translationKey, translations));
                    } catch (Exception e) {
                        throw new RuntimeException("未添加键 "+ translationKey + " ?", e);
                    }
                });
            }
        }
    }
}
