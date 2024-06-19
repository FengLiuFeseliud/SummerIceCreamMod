package fengliu.feseliud.utils;

public class HitSlot {
    /**
     * 2点击槽
     */
    public enum TwoHitSlot implements IHitSlot{
        SLOT1(0),
        SLOT2(1);

        private final int index;

        TwoHitSlot(int index){
            this.index = index;
        }


        @Override
        public int getIndex() {
            return this.index;
        }

        @Override
        public IHitSlot[] getHitSlots() {
            return TwoHitSlot.values();
        }
    }


    /**
     * 三点击槽
     */
    public enum ThreeHitSlot implements IHitSlot{
        SLOT1(0),
        SLOT2(1),
        SLOT3(2);

        private final int index;

        ThreeHitSlot(int index){
            this.index = index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        @Override
        public IHitSlot[] getHitSlots() {
            return ThreeHitSlot.values();
        }
    }
}
