package thd.game.level;

import java.awt.*;

/**
 * Class representing the first level with its properties.
 */
public class Level1 extends Level {
    /**
     * Creates the first level with its properties.
     */
    public Level1() {
        name = "FOREST";
        number = 1;
        backgroundColor = new Color(168, 112, 47);
        // B - Bush, T - Tank, W - Wooden Wall, R - Radioactive Pack
        // G - Ghost quadratic, g - Ghost triangular, p - Ghost linear
        // A - Accordion quadratic, a - Accordion triangular, o - Accordion linear
        // S - Spy quadratic, s - Spy triangular, z - Spy linear
        // b - left wall rocket pad, C - right wall rocket pad; c - top wall rocket pad, D - bottom wall rocket pad
        // d - rocket, E - chain rocket pad, e - black wall rocket pad, F - StartRamp, f - pilons, H - stone wall
        // h - centreline runway, I - Fence, i - Jet, J - Parkbox, j - safety box, K - Wall Airport, k - Wall Runway
        // L - Bush complete forest, l - 4 Bushes
        world = """
                L                                                                                                      \s
                BB                                                                                                 l   \s
                B  T                                           G   a                                               l   \s
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
