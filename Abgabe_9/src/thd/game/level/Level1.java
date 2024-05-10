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
        world = """
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                BB                                                                                                 BBB\s
                B  T                                           G   a                                               BBB\s
                B                                                                                                   BB\s
                B                                                                                                   BB\s
                B                                                                                                    B\s
                B                                                                                                    B\s
                B                               WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWBBBBBBBBBBBBBB                      B\s
                B                                                                BBBBBBBBBBBBBB                       \s
                B                                                                            G                        \s
                B                                                                                                     \s
                B                                                                                                     \s
                B                                                                                                     \s
                B              BB                                                                                     \s
                B              BB                 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                   B\s
                B                                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                  B\s
                B                              BBBB  S                            BB  B  B  BB                       B\s
                B                             BBBBB                                 BB BB BB            BB           B\s
                B                             BBBB   R                                                  BB           B\s
                B                            BBBBB                                                                   B\s
                B                             BBBBB                     z                                            B\s
                B   A                        BBBBB                                                                   B\s
                BB                          BBBBB                                                                    B\s
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
