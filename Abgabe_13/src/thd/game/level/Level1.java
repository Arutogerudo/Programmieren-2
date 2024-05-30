package thd.game.level;

import java.awt.*;

/**
 * Class representing the first level with its properties.
 */
public class Level1 extends Level {
    private static final Color BACKGROUND_COLOR = new Color(168, 112, 47);
    /**
     * Creates the first level with its properties.
     */
    public Level1() {
        name = "FOREST";
        number = 1;
        backgroundColor = new Color(BACKGROUND_COLOR.getRGB());
        world = """
                L                                                                                                      \s
                BB                                                                                                 l   \s
                B  T                                           G       a                                           l   \s
                B                                                                                                   BBB\s
                B                                                                                                   BBB\s
                B                                                                                                    BB\s
                B                                                                                                    BB\s
                B                               W                  W             l   l   l   BB                      BB\s
                B                                                                l   l   l   BB                        \s
                B                                                                            G                         \s
                B                                                                                                      \s
                B                                                                                                      \s
                B                                                                                                      \s
                B              BB                                                                                      \s
                B              BB                 l   l   l   l   l   l   l   l                                      BB\s
                B                                l   l   l   l   l   l   l   l   BB                                  BB\s
                B                              l     S                            BB  B  B  BB                       BB\s
                B                             l   B                                 BB BB BB            BB           BB\s
                B                             l      R                                                  BB           BB\s
                B                            Bl                                                                      BB\s
                B                             Bl                        z                                            BB\s
                B   A                        Bl                                                                      BB\s
                BB                          Bl                                                                       BB\s
                L                                                                                                      \s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s
                                                                                                                       \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
