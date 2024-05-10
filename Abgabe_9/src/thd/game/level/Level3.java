package thd.game.level;

import java.awt.*;

/**
 * Class representing the third level with its properties.
 */
public class Level3 extends Level {
    /**
     * Creates the third level with its properties.
     */
    public Level3() {
        name = "AIRPORT";
        number = 3;
        backgroundColor = new Color(65, 65, 65);
        // B - Bush, T - Tank, W - Wooden Wall, R - Radioactive Pack
        // G - Ghost quadratic, g - Ghost triangular, p - Ghost linear
        // A - Accordion quadratic, a - Accordion triangular, o - Accordion linear
        // S - Spy quadratic, s - Spy triangular, z - Spy linear
        // b - left wall rocket pad, C - right wall rocket pad; c - top wall rocket pad, D - bottom wall rocket pad
        // d - rocket, E - chain rocket pad, e - black wall rocket pad, F - StartRamp, f - pilons, H - stone wall
        // h - centreline runway, I - Fence, i - Jet, J - Parkbox, j - safety box, K - Wall Airport, k - Wall Runway
        world = """
                KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK|KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKBBBBKKKKKKKKKK|KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\s
                                                                   |                                     BBjBB         |i                                        i        J\s
                                                                   |                                    BBBBBB         |                   G                              J\s
                                                           o       |                                     BBBB          |                                                  J\s
                                                                   |         a                                         |                       JJJJJJJJ        JJJJJJJJJJJJ\s
                                                                   |                                                   |i                     i       J          i        J\s
                                                                   |                                                   |                              J                   J\s
                I            kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk|kkkkkkkkkkkkkkkkkkkkkkkkkkk                        |                              J                   J\s
                             k  R                                  |                                                   |JJJJJJJ                JJJJJJJJ        JJJJJJJJJJJJ\s
                I            k                                G    |                     G                             |                              J                   J\s
                             k                                     |                                                   |                      i       J                   J\s
                I            k  h ih  h  h  h  h  h  h  h  h  h  h |h  h  h  h  h  h  h  h  h                          |                              J                   J\s
                             k                                     |               i                                   |                              J               sR  J\s
                I            k                                     |                                   S               |                              J                   J\s
                             k                                     |                                                   |                              J                   J\s
                I            kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk|kkkkkkkkkkkkkkkkkkkkkkkkkkk                  hhhhhh|hhhhhhh           JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ\s
                                                                   |                                                   |                                                   \s
                I                                                  |                                                   |                                                   \s
                                                                   |                                                   |                                                   \s
                I   B                                     s        |                                                   |                                             o     \s
                   BBB                                             |                                                   |                                                   \s
                IBBBBB                                             |                                                   |                                                   \s
                BBBBBB                                             |                                                   |                                                   \s
                BBBBBKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK|KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK|KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\s
                                                                                                                                                                         \s
                                                                                                                                                                         \s
                                                                                                                                                                         \s
                                                                                                                                                                         \s
                                                                                                                                                                         \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
