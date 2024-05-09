package thd.game.level;

public class Level3 extends Level {
    public Level3() {
        name = "AIRPORT";
        number = 3;
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
                B                                              G   a                                               BBB\s
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
                B                                 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                   B\s
                B                                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                  B\s
                B                              BBBB  S                            BB  B  B  BB                       B\s
                B                             BBBBB                                 BB BB BB            BB           B\s
                B                             BBBB   R                                                  BB           B\s
                B                            BBBBB                                                                   B\s
                B                             BBBBB                     z                                            B\s
                B                            BBBBB                                                                   B\s
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
