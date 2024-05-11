package thd.game.level;

import java.awt.*;

/**
 * Class representing the second level with its properties.
 */
public class Level2 extends Level {
    /**
     * Creates the second level with its properties.
     */
    public Level2() {
        name = "ROCKET PAD";
        number = 2;
        backgroundColor = new Color(59, 182, 18);
        // B - Bush, T - Tank, W - Wooden Wall, R - Radioactive Pack
        // G - Ghost quadratic, g - Ghost triangular, p - Ghost linear
        // A - Accordion quadratic, a - Accordion triangular, o - Accordion linear
        // S - Spy quadratic, s - Spy triangular, z - Spy linear
        // b - left wall rocket pad, C - right wall rocket pad; c - top wall rocket pad, D - bottom wall rocket pad
        // d - rocket, E - chain rocket pad, e - black wall rocket pad, F - StartRamp, f - pilons, H - stone wall
        // h - centreline runway, I - Fence, i - Jet, J - Parkbox, j - safety box, K - Wall Airport, k - Wall Runway
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
                                                                  Cb        d                                        Cb\s
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
                b                                                           d                            z           Cb\s
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
