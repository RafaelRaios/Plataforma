package utils;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
        public static final int UP = 1;
        public static final int DOWN = 3;

    }
    public static class PlayerConstant {
        public static final int RUNNING = 1;
        public static final int IDLE = 0;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK1 = 6;
        public static final int ATTACK_JUMP1 = 7;
        public static final int ATTACK_JUMP2 = 8;

        public static int GetSpriteAmount(int player_action) {

            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case GROUND:
                    return 2;
                case ATTACK1:
                case ATTACK_JUMP1:
                case JUMPING:
                case ATTACK_JUMP2:
                    return 3;
                case FALLING:
                default:
                    return 1;
            }
        }

    }
}
