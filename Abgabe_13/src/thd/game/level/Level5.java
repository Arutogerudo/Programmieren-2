package thd.game.level;

import java.awt.*;

/**
 * Class representing the fifth level with its properties.
 */
public class Level5 extends Level {
    private static final Color BACKGROUND_COLOR = new Color(92, 157, 10);
    /**
     * Creates the fifth level with its properties.
     */
    public Level5() {
        name = "REFINERY";
        number = 5;
        backgroundColor = new Color(BACKGROUND_COLOR.getRGB());
        world = """
                P                                                  x                     tXXXXXXXXXXXXXXXXXXXXXXXXXXXu\s
                Q                                                                                                    r\s
                                                                              G                                      r\s
                                                                                                                     r\s
                                              o                                                                      r\s
                                                                                  tXXXXXXXXXXXXXXXXXXXXXu            r\s
                                                             tXXXXqY q            r u                 t r            r\s
                          tXXXXXXXXXXXXXXXXXXXXXXXu          r                    r r                 r r            r\s
                          r                       r          wXXXX                r r                 r r            r\s
                          r  tXu             GR   r                               r r                 r r            r\s
                          r  r r                  r                               wXv                 wXv            r\s
                          r  r v                  r                                                                  r\s
                          r  r                    r                                                                  r\s
                          r  r                    r                                                                  r\s
                          r  r                    r                                                                t r\s
                          r  r      tXu           r                                                                r r\s
                          r  r      r             r                    XXXXXXXXXXXXXXXXXXu                         wXv\s
                          r  r      wXXXXXXXXXXXXXv  S                                   r     S                      \s
                          r  r                                                           r                            \s
                          r  r                                                           r                            \s
                T         r  r                                                     o     r                            \s
                          r  r                                                           r                            \s
                          r  r                                                           r                            \s
                P                                                   x                    rx                   x       \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s""";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}
