package thd.game.level;

import java.awt.*;

/**
 * Class representing the fourth level with its properties.
 */
public class Level4 extends Level {
    private static final Color BACKGROUND_COLOR = new Color(162, 162, 162);
    /**
     * Creates the fourth level with its properties.
     */
    public Level4() {
        name = "BRIDGE";
        number = 4;
        backgroundColor = new Color(BACKGROUND_COLOR.getRGB());
        world = """
                m                m                m                m          m                m                m                m                m               m       \s
                m                m                m                           m                m                m                m                                       O\s
                M                m                                            m                m                m                m                                        \s
                                 m                                                                                                 a                                      \s
                                 m                           G                         g                                                                                  \s
                                 m                                                                                     S                          N                       \s
                                 m                                                                                                                m                       \s
                                                          n                     N                                                                                         \s
                                                                                m                                                                                         \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                 o                                                                        \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                  A                                                                             N                N               M                        \s
                                                                                                                m                m                                        \s
                                 N                                                  N                                                                                     \s
                T        z       N                                                  N                                                                                     \s
                                 N                                      z           N                                                 o                                   \s
                                 N                                                  N                                                       R                             \s
                                 N                                                  N                                                                                     \s
                N                N                N                N                N                N                N                N                N                N\s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
