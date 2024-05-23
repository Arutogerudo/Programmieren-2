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
                K                                                  |K                                    BBBB          K                                                   \s
                                                                   |                                     BBjBB         i                                        i        JJ\s
                                                                   |                                    BBBBBB                            G                              JJ\s
                                                           o       |                                     BBBB                                                            JJ\s
                                                                   |         a                                                                JJJJJJJJ        JJJJJJJJJJJJJ\s
                                                                   |                                                   i                     i       J          i        JJ\s
                                                                   |                                                                                 J                   JJ\s
                I            kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk|kkkkkkkkkkkkkkkkkkkkkkkkkkk                                                      J                   JJ\s
                             k  R                                  |                                                   JJJJJJJ                JJJJJJJJ        JJJJJJJJJJJJJ\s
                             k                                G    |                     G                                                           J                   JJ\s
                             k                                     |                                                                         i       J                   JJ\s
                             k  h ih  h  h  h  h  h  h  h  h  h  h |h  h  h  h  h  h  h  h  h                                                        J                   JJ\s
                             k                                     |               i                                                                 J               sR  JJ\s
                             k                                     |                                   S                                             J                   JJ\s
                             k                                     |                                                                                 J                   JJ\s
                             kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk|kkkkkkkkkkkkkkkkkkkkkkkkkkk                  hhhhhhhhhhhhh           JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ\s
                                                                   |                                                                                                      \s
                                                                   |                                                                                                      \s
                                                                   |                                                                                                      \s
                    B                                     s        |                                                                                                o     \s
                   BBB                                             |                                                                                                      \s
                 BBBBB                                             |                                                                                                      \s
                BBBBBB                                             |                                                                                                      \s
                KBBBB                                              |K                                                  K                                                  \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s
                                                                                                                                                                          \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
