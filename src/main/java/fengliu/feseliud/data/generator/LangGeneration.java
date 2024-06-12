package fengliu.feseliud.data.generator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.SummerIceCreamDataGenerator;
import fengliu.feseliud.item.BaseColorItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.utils.level.IItemLevel;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;

import java.io.IOException;
import java.io.Reader;
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

    private void generateLevelItemTranslations(String type, JsonObject translations, TranslationBuilder translationBuilder, Object objMaps){
        if (!(objMaps instanceof Map<?,?> maps)){
            return;
        }

        maps.forEach((objItem, levelItem) -> {
            if(!(objItem instanceof Item item) || !(levelItem instanceof IItemLevel level)){
                return;
            }

            String translationKey = type + "." + SummerIceCream.MOD_ID + "." + level.getName();
            try {
                String langData =  level.getTranslations(translationKey, translations);
                if (langData == null){
                    return;
                }
                translationBuilder.add(item, langData);
            } catch (Exception e) {
                throw new RuntimeException("未添加键 "+ translationKey + " ?", e);
            }
        });
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

        SummerIceCreamDataGenerator.forFields(ModItems.class.getDeclaredFields(), obj -> {
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

            this.generateLevelItemTranslations("item", translations, translationBuilder, obj);
        });

        SummerIceCreamDataGenerator.forFields(ModBlockItems.class.getDeclaredFields(), obj -> this.generateLevelItemTranslations("block", translations, translationBuilder, obj));
    }
}
