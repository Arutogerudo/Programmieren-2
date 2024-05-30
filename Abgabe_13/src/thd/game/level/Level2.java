package thd.game.level;

import java.awt.*;

/**
 * Class representing the second level with its properties.
 */
public class Level2 extends Level {
    private static final Color BACKGROUND_COLOR = new Color(59, 182, 18);
    /**
     * Creates the second level with its properties.
     */
    public Level2() {
        name = "ROCKET PAD";
        number = 2;
        backgroundColor = new Color(BACKGROUND_COLOR.getRGB());
        world = """
                bc                                                Cbc                                                  \s
                bd                                             R  Cb                                                   \s
                b                         S                       Cb  p                                                \s
                bd                                                Cb                                                   \s
                b                                          d      Cb                                                   \s
                b                                                 Cb        d    H                                     \s
                bE                                      e         Cb                       d      d                    \s
                                                                  Cb        d                           F            Cb\s
                                                                  Cb                        e                        Cb\s
                T                                                 Cb        d                                        Cb\s
                               F   f  f  F                        Cb                                                 Cb\s
                              G                                   Cb        d                           F            Cb\s
                                   f  f                           Cb                                                 Cb\s
                b                                                 Cb        d            A                           Cb\s
                b                  f  f                           Cb                                                 Cb\s
                b                                                 Cb        d                                        Cb\s
                b                                                 Cb                                                 Cb\s
                b                                                           d                                        Cb\s
                b                                                                                                    Cb\s
                b                                                           d                                        Cb\s
                b                              A                                                                 R   Cb\s
                b                                                           d                                z       Cb\s
                b                                                                                                    Cb\s
                bD                                                 D                                                 Cb\s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
