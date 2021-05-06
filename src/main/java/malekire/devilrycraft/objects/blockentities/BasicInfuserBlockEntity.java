package malekire.devilrycraft.objects.blockentities;

import com.tfc.minecraft_effekseer_implementation.common.Effek;
import com.tfc.minecraft_effekseer_implementation.common.api.EffekEmitter;
import com.tfc.minecraft_effekseer_implementation.meifabric.NetworkingFabric;
import malekire.devilrycraft.inventory.BasicInfuserInventory;
import malekire.devilrycraft.vis_system.Vis;
import malekire.devilrycraft.vis_system.VisTaint;
import malekire.devilrycraft.vis_system.VisType;
import malekire.devilrycraft.recipies.BasicInfuserRecipe;
import malekire.devilrycraft.recipies.Type;
import malekire.devilrycraft.screen_stuff.screen_handlers.BasicInfuserScreenHandler;
import malekire.devilrycraft.common.DevilryBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static malekire.devilrycraft.vis_system.VisType.TAINT;
import static malekire.devilrycraft.vis_system.VisType.VIS;

public class BasicInfuserBlockEntity extends VisBlockEntity implements NamedScreenHandlerFactory, BasicInfuserInventory, BlockEntityClientSerializable {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(13, ItemStack.EMPTY);

    ItemStack clientStack = null;
    ItemStack serverStack = null;
    ItemStack NOTHING_STACK = new ItemStack(Items.AIR, 2);
    public Effek efk;
    public EffekEmitter emitter;
    public int currentCraftingTicks = 0;
    public ArrayList<BlockPos> neighborVisBlocks = new ArrayList<>();

    public BasicInfuserBlockEntity() {
        super(DevilryBlockEntities.BASIC_INFUSER_BLOCK_ENTITY);
        maxVisTaint = new VisTaint(1000, 1000);


    }


    @Override
    public void removeVis(double amount) {
        this.Extract(VIS, amount);
    }

    @Override
    public void removeTaint(double amount) {
        this.Extract(TAINT, amount);
    }



    @Override
    public boolean CanContainVisType(VisType type) {
            return true;
    }

    @Override
    public double ExtractionRate(VisType type) {
        return 5;
    }

    @Override
    public double InsertionRate(VisType type) {
        return 5;
    }


    @Override
    public double MaxLevel(VisType type) {
        if(type == VIS)
            return this.maxVisTaint.visLevel;
        return this.maxVisTaint.taintLevel;
    }

    public void testCraft() {
        List<BasicInfuserRecipe> match = world.getRecipeManager()
                .getAllMatches(Type.INSTANCE, this, world);

        for(int i = 0; i < match.size(); i++)
        {
            if(match.get(i).matches(this, world))
            {
                isDirty = true;
                currentCraftingTicks++;
                if(currentCraftingTicks >= match.get(i).TICKS)
                {
                    currentCraftingTicks = 0;
                    doCraft(match.get(i));
                }
            }
        }

    }
    boolean spawnEffect = false;
    @Override
    public void tick() {
        if(!(world instanceof ClientWorld) && spawnEffect == false) {
            NetworkingFabric.sendStartEffekPacket(Objects.requireNonNull(this.getWorld()), new Identifier("devilry_craft:fire_orb"), new Identifier("devilry_craft:effeks"), 0, new Vector3d(getPos().getX(), getPos().getY(), getPos().getZ()));
            spawnEffect = true;
        }
        if(!world.isClient()) {

           // NetworkingFabric.sendStartEffekPacket(this.getWorld(), new Identifier("devilry_craft:fire_orb"), new Identifier("devilry_craft:effeks"), 0, new Vector3d(getPos().getX(), getPos().getY(), getPos().getZ()));
            testCraft();
        }
        //emitter.setPosition(getPos().getX(), getPos().getY()+2, getPos().getZ());
        if(!world.isClient() && isDirty) {


            //Syncing visual animations
            isDirty = false;
            this.sync();

            //need to move outside of the current area once we figure out how to do it with less lag.
            //Crafting stuff


            //Transfering Vis Between Containers



        }
        if(!world.isClient()) {
            if(!this.IsFull()) {
                for (BlockPos offsetPos : neighborVisBlocks) {
                    Vis blockEntity = (Vis) getWorld().getBlockEntity(offsetPos);
                    blockEntity.Extract(VIS, this.InsertionRate(VIS), this);
                    blockEntity.Extract(TAINT, this.InsertionRate(TAINT), this);
                    if(blockEntity instanceof  VisBlockEntity)
                    {
                        //((VisBlockEntity)blockEntity).isDirty = true;
                    }
                }
            }
        }





    }

    public void doCraft(BasicInfuserRecipe recipe) {
        if(this.getItems().get(12).getCount() == 1) {
            for (int i2 = 0; i2 < this.size(); i2++) {
                if (!this.getItems().get(i2).isEmpty()) {
                    this.getItems().get(i2).decrement(1);
                }
            }
            this.getItems().set(12, recipe.craft(this));

        }

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public double getVis() {
        return GetLevel(VIS);
    }

    @Override
    public double getTaint() {
        return GetLevel(TAINT);
    }

    @Override
    public int size() {
        return BasicInfuserInventory.super.size();
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("Infuser");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BasicInfuserScreenHandler(syncId, inv, this);
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {

        Inventories.toTag(tag, this.inventory);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        clear();

        Inventories.fromTag(tag, getItems());

    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        super.toTag(tag);
        toTag(tag);
        return tag;
    }
    @Override
    public void markDirty() {
        super.markDirty();
        isDirty = true;
    }






}
