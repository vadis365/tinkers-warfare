package com.nianor.tinkersarsenal.tinkers.tools.Chamber;

import com.nianor.tinkersarsenal.CommonProxy;
import com.nianor.tinkersarsenal.TinkersArsenal;
import com.nianor.tinkersarsenal.tinkers.material.*;
import com.nianor.tinkersarsenal.tinkers.tools.Action.ActionCore;
import com.nianor.tinkersarsenal.tinkers.tools.Barrel.BarrelCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.ClientProxy;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.library.utils.TooltipBuilder;

import java.util.ArrayList;
import java.util.List;

public class SingleCore extends ChamberCore {

    public SingleCore () {
        super(ArsenalPartMaterialTypes.core(CommonProxy.SHAFT), ArsenalPartMaterialTypes.face(CommonProxy.STURDY_TUBING), ArsenalPartMaterialTypes.mech(CommonProxy.HEAVY_SPRING));
    }
    @Override
    protected MechanicalNBT buildTagData(List<Material> materials) {
        ArrayList<Integer> coreMaterials = new ArrayList<Integer>();
        ArrayList<Integer> faceMaterials = new ArrayList<Integer>();
        ArrayList<Integer> mechMaterials = new ArrayList<Integer>();

        coreMaterials.add(CommonProxy.SHAFT.getCost());
        faceMaterials.add(CommonProxy.STURDY_TUBING.getCost());
        mechMaterials.add(CommonProxy.HEAVY_SPRING.getCost());

        CoreMaterialStats shaft = materials.get(0).getStatsOrUnknown(ArsenalMaterialTypes.CORE);
        FaceMaterialStats sturdy_tubing = materials.get(1).getStatsOrUnknown(ArsenalMaterialTypes.FACE);
        MechMaterialStats heavy_spring = materials.get(2).getStatsOrUnknown(ArsenalMaterialTypes.MECH);
        MechanicalNBT data = new MechanicalNBT();
        data.core(coreMaterials, shaft);
        data.face(faceMaterials, sturdy_tubing);
        data.mech(mechMaterials, heavy_spring);
        return data;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return false;
    }
    @Override
    public List<String> getInformation(ItemStack stack, boolean detailed)
    {
        TooltipBuilder info = new TooltipBuilder(stack);

        MechanicalNBT nbt = MechanicalNBT.from(stack);
        info.addDurability(!detailed);
        info.add(MechMaterialStats.formatWeight(nbt.mech_effectiveness));
/*
        if (nbt.duration < 0)
            info.add(String.format("%s: %s%s", Util.translate(YoyoNBT.LOC_Duration), AxleMaterialStats.COLOR_Friction, Util.translate(YoyoNBT.LOC_Infinite)) + TextFormatting.RESET);
        else
            info.add(String.format("%s: %s%s %s", Util.translate(YoyoNBT.LOC_Duration), AxleMaterialStats.COLOR_Friction, Util.df.format(nbt.duration / 20F), Util.translate(YoyoNBT.LOC_Suffix)) + TextFormatting.RESET);
*/
        info.add(CoreMaterialStats.formatWeight(nbt.weight));

        if (ToolHelper.getFreeModifiers(stack) > 0)
            info.addFreeModifiers();

        if (detailed)
            info.addModifierInfo();

        return info.getTooltip();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public Material getMaterialForPartForGuiRendering(int index)
    {
        switch(index)
        {
            case 0: return ClientProxy.RenderMaterialString;
            case 1: return ClientProxy.RenderMaterials[2];
            case 2: return ClientProxy.RenderMaterials[1];
            case 3: return ClientProxy.RenderMaterials[0];
            default: return super.getMaterialForPartForGuiRendering(index);
        }
    }



    public float getWeight(ItemStack single)
    {
        return MechanicalNBT.from(single).weight;
    }

    public float getMech(ItemStack single)
    {
        return MechanicalNBT.from(single).mech_effectiveness;
    }

    public int getDurability(ItemStack single)
    {
        return MechanicalNBT.from(single).durability;
    }

    public int getDamage(ItemStack single)
    {
        return MechanicalNBT.from(single).damage;
    }

    public float getHardness(ItemStack single)
    {
        return MechanicalNBT.from(single).hardness;
    }

    public void damageItem(ItemStack single, EntityLivingBase player, int damage)
    {
        ToolHelper.damageTool(single, damage, player);
    }

    @SideOnly(Side.CLIENT)
    public int getCoreColor(ItemStack single)
    {
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(single));
        return materials.get(0).materialTextColor;
    }

    @SideOnly(Side.CLIENT)
    public int getFaceColor(ItemStack single)
    {
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(single));
        return materials.get(1).materialTextColor;
    }

    @SideOnly(Side.CLIENT)
    public int getMechColor(ItemStack single)
    {
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(single));
        return materials.get(2).materialTextColor;
    }





}
