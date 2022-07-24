package online.cszt0.magicworld.worldgen.structure;

import java.util.ArrayList;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;

import net.minecraft.structure.EndCityGenerator;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class TestStructure extends Structure {
    public static final Codec<TestStructure> CODEC = TestStructure.createCodec(TestStructure::new);

    public TestStructure(Structure.Config config) {
        super(config);
    }

    @Override
    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        BlockRotation blockRotation = BlockRotation.random(context.random());
        BlockPos blockPos = this.getShiftedPos(context, blockRotation);
        if (blockPos.getY() < 60) {
            return Optional.empty();
        }
        return Optional.of(new Structure.StructurePosition(blockPos,
                collector -> this.addPieces((StructurePiecesCollector) collector, blockPos, blockRotation, context)));
    }

    private void addPieces(StructurePiecesCollector collector, BlockPos pos, BlockRotation rotation,
            Structure.Context context) {
        ArrayList<StructurePiece> list = Lists.newArrayList();
        TestGenerator.addPieces(context.structureTemplateManager(), pos, rotation, list, context.random());
        list.forEach(collector::addPiece);
    }

    @Override
    public StructureType<?> getType() {
        return StructureType.END_CITY;
    }
}
