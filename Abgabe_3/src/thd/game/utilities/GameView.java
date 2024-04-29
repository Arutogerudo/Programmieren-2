package thd.game.utilities;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ein Fenster, welches das einfache Gestalten von Spielen erlaubt. Es wird eine Leinwand mit einer Auflösung von
 * {@value WIDTH} * {@value HEIGHT} Pixeln erzeugt (Breite = {@value WIDTH} Pixel, Höhe = {@value HEIGHT} Pixel).
 * <br><br><pre><code>
 *   (0,0) . . . . . . . . . . . ({@value WIDTH},0)<br>
 *         . . . . . . . . . . .<br>
 *         . . . . . . . . . . .<br>
 *         . . . . . . . . . . .<br>
 * (0,{@value HEIGHT}) . . . . . . . . . . . ({@value WIDTH},{@value HEIGHT})
 * <br></code></pre>
 * Zusätzlich kann Ton ausgegeben werden und Tastatur- und Mausereignisse können abgefragt werden.
 *
 * <ol>
 *     <li>Sie können die Methode {@link #print(String, int, boolean, double)} statisch aufrufen, um Texte anzuzeigen. Ein Fenster
 *   wird automatisch geöffnet und für weitere Ausgaben mit derselben Methode offen gehalten. Beispiel:
 *   <pre>{@code
 *   GameView.print("Hallo", 20, true);
 *   }</pre></li>
 *     <li>
 * Sie können eine Klasse von GameView erben lassen. Dann können Sie verschiedene Methoden zur Darstellung nutzen. Es
 * wird ein Game-Loop gestartet, der 60 Mal pro Sekunde ausgeführt wird. Beispiel:
 * <pre>{@code
 *
 * package demo;
 * import view.GameView;
 * import java.awt.*;
 *
 * public class GameViewManager extends GameView {
 *   @Override
 *   public void initialize() {
 *     setWindowTitle("Mars Invasion II");
 *     setStatusText("Java Programmierung SS 2022");
 *   }
 *
 *   @Override
 *   public void gameLoop() {
 *     addTextToCanvas("Hallo", 0, 0, 20, false, Color.WHITE, 0);
 *   }
 * }
 * }</pre>
 * Sie müssen ein Objekt der Klasse <code>GameViewManager</code> erzeugen und die Methode {@link #startGame()} aufrufen
 * um den Game-Loop zu starten.
 *     </li>
 * </ol>
 *
 * @author Andreas Berl
 */
public abstract class GameView {

    /**
     * Breite der Leinwand in Pixeln.
     */
    public static final int WIDTH = 1280;

    /**
     * Höhe der Leinwand in Pixeln.
     */
    public static final int HEIGHT = 720;

    private static int instances;
    private static GameView instance;
    private final Canvas canvas;
    private final Mouse mouse;
    private final Keyboard keyboard;
    private final Sound sound;
    private final SwingAdapter swingAdapter;
    private final Statistic statistic;
    private final Tools tools;
    private final GameLoop gameLoop;
    private final long startTimeInMilliseconds;
    private final HashMap<Integer, Long> timers;
    private boolean showScreen;
    private boolean gameViewClosed;

    protected GameView() {
        instances++;
        gameViewClosed = true;
        startTimeInMilliseconds = System.currentTimeMillis();
        statistic = new Statistic();
        swingAdapter = new SwingAdapter(statistic);
        mouse = new Mouse(swingAdapter);
        keyboard = new Keyboard();
        sound = new Sound();
        canvas = new Canvas();
        gameLoop = new GameLoop();
        swingAdapter.registerListeners(mouse, keyboard, sound);
        timers = new HashMap<>(200);
        tools = new Tools();
        tools.checkResources();
    }

    /**
     * Diese Methode wird einmal aufgerufen, bevor der Game-Loop gestartet wird. Sie sollte genutzt werden, um GameView
     * vollständig zu konfigurieren und die Spiel-Elemente zu erzeugen. Erzeugen Sie eine Klasse, die von GameView erbt
     * und überschreiben Sie diese Methode.
     */
    public abstract void initialize();

    /**
     * Der Game-Loop wird nach der methode {@link #initialize()} gestartet. Er wird 60 Mal pro Sekunde aufgerufen. Nach
     * jedem Aufruf wird der Inhalt des Canvas gezeichnet. Erzeugen Sie eine Klasse, die von GameView erbt und
     * überschreiben Sie diese Methode.
     */
    public abstract void gameLoop();

    /**
     * Das Spiel wird gestartet. <br>
     * <ol>
     *     <li>Die Methode {@link #initialize()} wird aufgerufen.</li>
     *     <li>Das GameView-Fenster wird geöffnet.</li>
     *     <li>Die Methode {@link #gameLoop()}  wird 60 Mal pro Sekunde aufgerufen.</li>
     * </ol>
     * Diese Methode blockiert bis das Fenster geschlossen wird.
     */
    public void startGame() {
        if (!gameViewClosed) {
            throw new IllegalStateException("Der GameLoop wurde bereits gestartet.");
        }
        gameViewClosed = false;
        swingAdapter.initialize();
        initialize();
        while (!gameViewClosed) {
            gameLoop.gameLoopWithStatistics();
        }
    }

    /**
     * Zeigt eine Statistik in der linken oberen Ecke des Bildschirms. Diese Statistik kann jederzeit mit dem
     * Tastaturkürzel <b>STRG+R</b> ein- und ausgeschaltet werden.
     * <br><br>
     * <b>Loops/Sekunde</b>: Zeigt an, wie oft der Loop pro Sekunde ausgeführt wird. Dieser Wert sollte knapp unter 60
     * liegen. Falls der Wert kleiner ist, schafft Ihr Rechner die Berechnungen nicht<br><br>
     * <b>Bilder/Sekunde:</b> Zeigt an, wie viele Frames pro Sekunde auf das Fenster gezeichnet werden. Dieser Wert
     * sollte knapp unter 60 liegen. Falls der Wert kleiner ist, wurden einzelne Frames nicht gezeichnet, weil die
     * Grafikkarte zu langsam war.<br><br>
     * <b>GameView:</b> Zeigt an, wie viel Zeit GameView pro Frame braucht um ein Bild aufzubereiten.<br><br>
     * <b>Grafikkarte:</b> Zeigt an, wie viel Zeit die Grafikdarstellung pro Bild braucht um es aufs Fenster zu
     * zeichnen.<br><br>
     * <b>Spiel-Logik:</b> Zeigt an, wie viel Zeit die von Ihnen implementierte Spiel-Logik benötigt, um ein Bild zu
     * berechnen. Es handelt sich dabei um den Code, der in der Methode <code>gameLoop()</code> ablauft. Dieser Wert
     * sollte etwa
     * <b>1</b> betragen.<br><br>
     * <b>Sichtbar:</b> Zeigt an, wie viel Spielobjekte gerade zu sehen sind. Dieser Wert sollte <b>kleiner 200</b>
     * sein.<br><br>
     * <b>Unsichtbar:</b> Zeigt an, wie viel Spielobjekte gerade zu nicht sehen sind, aber trotzdem berechnet werden.
     * Dieser Wert sollte bei <b>0</b> liegen.<br><br>
     * <b>Größe:</b> Zeigt an, wie viel Speicher für Bilder benötigt wird. Dieser Wert sollte unter <b>500 MB</b>
     * liegen.<br><br>
     * <b>Überläufe:</b> Zeigt an, wie oft der Bildspeicher schon gelöscht werden musste, weil zu viel Speicher
     * benötigt wurde. Dieser Wert sollte bei <b>0</b> liegen.<br><br>
     *
     * @param show True, falls die Statistik angezeigt werden soll.
     */
    public void showStatistic(boolean show) {
        statistic.showStatistics = show;
    }

    /**
     * Gibt alle Dateien aus dem Pfad "resources" zurück. Funktioniert nicht, falls diese Methode innerhalb eines
     * ".jar-Files" aufgerufen wird. Dann wird eine leere Liste zurückgegeben.
     *
     * @return Dateien aus dem Pfad "resources".
     */
    public List<Path> getResourceFiles() {
        return tools.resourceFiles;
    }

    /**
     * Setzt den Fenstertitel.
     *
     * @param title Der Fenstertitel
     */
    public void setWindowTitle(String title) {
        swingAdapter.setTitle(title + " - " + Version.getStandardTitle());
    }

    /**
     * Legt ein Symbol für die Titelleiste fest. Das Symbolfile muss in einem Verzeichnis "src/resources" liegen. Bitte
     * den Namen des Files ohne Verzeichnisnamen angeben, z.B.<code>setWindowIcon("Symbol.png")</code>.
     *
     * @param iconFileName Der Dateiname des Symbols.
     */
    public void setWindowIcon(String iconFileName) {
        swingAdapter.setWindowIcon(iconFileName);
    }

    /**
     * Text, der in der Statuszeile angezeigt wird.
     *
     * @param statusText Text der Statuszeile.
     */
    public void setStatusText(String statusText) {
        swingAdapter.setStatusText(statusText);
    }

    /**
     * Setzt die Hintergrundfarbe.
     *
     * @param backgroundColor Hintergrundfarbe
     */
    public void changeBackgroundColor(Color backgroundColor) {
        canvas.setBackgroundColor(backgroundColor);
    }

    /**
     * Fügt eine neue Farbe zur Farbtabelle für Block-Grafiken hinzu oder überschreibt eine vorhandene Farbe mit neuen
     * Werten.
     * <pre>
     * <code>
     * <br>
     * Die bereits vordefinierte Farbtabelle:
     * 'R' = Color.RED
     * 'r' = Color.RED.brighter()
     * 'G' = Color.GREEN
     * 'g' = Color.GREEN.brighter()
     * 'B' = Color.BLUE
     * 'b' = Color.BLUE.brighter()
     * 'Y' = Color.YELLOW
     * 'y' = Color.YELLOW.brighter()
     * 'P' = Color.PINK
     * 'p' = Color.PINK.brighter()
     * 'C' = Color.CYAN
     * 'c' = Color.CYAN.brighter()
     * 'M' = Color.MAGENTA
     * 'm' = Color.MAGENTA.brighter()
     * 'O' = Color.ORANGE
     * 'o' = Color.ORANGE.brighter()
     * 'W' = Color.WHITE
     * 'L' = Color.BLACK
     * </code>
     * </pre>
     *
     * @param character Buchstabe, der der Farbe zugeordnet ist.
     * @param color     Die Farbe, die dem Buchstaben zugeordnet ist.
     */
    public void setColorForBlockImage(char character, Color color) {
        swingAdapter.setColorForBlockImage(character, color);
    }

    /**
     * Es wird ein Startbildschirm mit einem Auswahlmenü angezeigt. Die Auswahl des Benutzers wird zurückgegeben.
     *
     * @param title          Titel des Programms.
     * @param description    Beschreibung des Programms. Achtung, es steht nicht viel Platz zur Verfügung. An den
     *                       passenden Stellen müssen Zeilenumbrüche eingefügt werden.
     * @param selectionTitle Titel des Auswahlmenüs.
     * @param selectionItems Einträge des Auswahlmenüs.
     * @param selectedItem   Gibt an, welcher Eintrag vorselektiert sein soll. Der erste Eintrag hat den Wert 0.
     * @return Der vom Benutzer gewählte Eintrag. Der erste Eintrag hat den Wert 0.
     */
    public int showStartScreenWithChooseBox(String title, String description, String selectionTitle, String[] selectionItems, int selectedItem) {
        if (gameViewClosed) {
            throw new IllegalStateException(Tools.GAMEVIEW_CLOSED);
        }
        showScreen = true;
        StartScreenWithChooseBox startScreenWithChooseBox = new StartScreenWithChooseBox(this, title, description, selectionTitle, selectionItems, selectedItem);
        keyEvents();
        startScreenWithChooseBox.printStartScreen();
        showScreen = false;
        return startScreenWithChooseBox.getSelectedItem();
    }

    /**
     * Es wird ein Startbildschirm mit einem einfachen Auswahlmenü angezeigt: "Easy", "Standard" und "Close Game". Die
     * Auswahl des Benutzers wird zurückgegeben. Falls der Benutzer "Beenden" wählt, wird das Programm sofort beendet.
     *
     * @param title       Titel des Programms.
     * @param description Beschreibung des Programms. Achtung, es steht nicht viel Platz zur Verfügung. An den passenden
     *                    Stellen müssen Zeilenumbrüche eingefügt werden.
     * @param easy        Die Vorauswahl der Schwierigkeitsstufe. Falls true, wir die Schwierigkeitsstufe "Easy"
     *                    vorausgewählt, ansonsten "Standard".
     * @return true falls der Spieler "Easy" gewählt hat, ansonsten false.
     */
    public boolean showSimpleStartScreen(String title, String description, boolean easy) {
        if (gameViewClosed) {
            throw new IllegalStateException(Tools.GAMEVIEW_CLOSED);
        }
        showScreen = true;
        SimpleStartScreen simpleStartScreen = new SimpleStartScreen(this, title, description, easy);
        keyEvents();
        simpleStartScreen.printStartScreen();
        String result = simpleStartScreen.getSelectedItem();
        if (result.equals(Tools.CLOSE)) {
            swingAdapter.closeWindow(true);
        }
        showScreen = false;
        return simpleStartScreen.getSelectedItem().equals("Easy");
    }

    /**
     * Es wird ein Endbildschirm mit einem einfachen Auswahlmenü angezeigt: "Neu starten" und "Beenden". Falls der
     * Benutzer "Beenden" wählt, wird das Programm sofort beendet.
     *
     * @param message Nachricht, die der Benutzer angezeigt bekommt.
     */
    public void showEndScreen(String message) {
        if (gameViewClosed) {
            throw new IllegalStateException(Tools.GAMEVIEW_CLOSED);
        }
        showScreen = true;
        EndScreen endScreen = new EndScreen(this, message);
        keyEvents();
        endScreen.printEndScreen();
        if (!endScreen.playAgain()) {
            swingAdapter.closeWindow(true);
        }
        showScreen = false;
    }

    /**
     * Diese Methode kann bunte Block-Grafiken anzeigen. Dazu muss ein farbcodierter <code>String</code> übergeben
     * werden, der dann auf die Leinwand (Canvas) übertragen wird, ohne die bisherigen Inhalte zu löschen. Die im
     * <code>String</code> enthaltenen Buchstaben werden als Farben interpretiert. Jeder Buchstabe repräsentiert einen
     * Block mit der Größe blockSize * blockSize. Beispiel: Ein rotes Dreieck mit grüner Füllung.
     * <br>
     * <pre>
     * <code>
     * <br>
     * String dreieck =
     * "   R   \n" +
     * "  RGR  \n" +
     * " RGGGR \n" +
     * "RRRRRRR\n";
     * <br>
     * </code>
     * </pre>
     * Um die Farbcodes zu interpretieren, wird eine Farbpalette ausgewertet. Die Farben der Farbpalette lassen sich
     * über die Methode {@link #setColorForBlockImage(char, Color)} anpassen.
     * <p>
     * Es sind nur Zeichen erlaubt, die in der Farbpalette vorkommen, das Leerzeichen (Space) und Zeilenumbrüche. Das
     * Leerzeichen ist transparent, man kann den Hintergrund sehen. Zusätzlich werden Koordinaten ausgewertet: (0, 0)
     * ist links oben {@link #GameView()}. Negative Koordinaten können verwendet werden, um Grafiken teilweise
     * anzuzeigen.
     * <p>
     * Die Größe der Blöcke muss mit dem Parameter <code>blockSize</code> festgelegt werden. Beispielsweise bedeutet
     * <code>blockSize = 10</code>, dass ein Block die Fläche von 10 mal 10 Pixeln belegt.
     * <p>
     * Die Grafik kann mit einer Rotation dargestellt werden, dabei wird um den Mittelpunkt der Grafik rotiert. Eine
     * Rotation um 0° stellt das Bild ohne Rotation dar, bei 180° steht das Bild auf dem Kopf.
     *
     * @param blockImage Das Bild als farbcodierter String.
     * @param x          x-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist links.
     * @param y          y-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist oben.
     * @param blockSize  Die Größe eines einzelnen Farbblocks.
     * @param rotation   Die Rotation des Bildes in Grad um den Mittelpunkt.
     * @see #setColorForBlockImage(char, Color)
     */
    public void addBlockImageToCanvas(String blockImage, double x, double y, double blockSize, double rotation) {
        BufferedImage image = swingAdapter.createImageFromColorString(blockImage, blockSize);
        addImageToCanvasIfVisible(image, x, y, rotation);
    }

    /**
     * Schreibt den übergebenen Text auf die Leinwand (Canvas), ohne die bisherigen Inhalte zu löschen. Zusätzlich
     * werden Koordinaten ausgewertet: (0, 0) entspricht links oben {@link #GameView()}. Negative Koordinaten können
     * verwendet werden, um Texte teilweise anzuzeigen. Leerzeichen sind durchsichtig (Objekte im Hintergrund sind zu
     * sehen). Die Schrift kann mit einer Rotation dargestellt werden, dabei wird um den Mittelpunkt der Grafik rotiert.
     * Eine Rotation um 0° stellt die Schrift ohne Rotation dar, bei 180° steht die Schrift auf dem Kopf. Es wird eine
     * Standard-Schriftart der Familie "Monospaced" verwendet. Die Darstellung kann sich auf unterschiedlichen
     * Betriebssystemen unterscheiden. Es gibt eine zweite Methode
     * {@link #addTextToCanvas(String, double, double, double, boolean, Color, double, String)} , um die Schriftart
     * festzulegen.
     *
     * @param text     Der anzuzeigende Text.
     * @param x        x-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist links.
     * @param y        y-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist oben.
     * @param fontSize Die Schriftgröße, mindestens 5.
     * @param bold     Die Schriftart. Bei true wird die Schrift fettgedruckt.
     * @param color    Die Farbe, in der der Text angezeigt werden soll.
     * @param rotation Die Rotation der Schrift in Grad um den Mittelpunkt.
     */
    public void addTextToCanvas(String text, double x, double y, double fontSize, boolean bold, Color color, double rotation) {
        addTextToCanvas(text, x, y, fontSize, bold, color, rotation, "standardfont");
    }

    /**
     * Diese Methode hat dieselbe Funktionalität wie die Methode
     * {@link #addTextToCanvas(String, double, double, double, boolean, Color, double)}. Darüber hinaus kann eine
     * Schriftart als .ttf-Font-Datei angegeben werden. Die Schrift wird mit dem angegebenen Font ausgegeben. Dieser
     * Font wird auf allen Betriebssystemen gleich dargestellt.
     *
     * @param text     Der anzuzeigende Text.
     * @param x        x-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist links.
     * @param y        y-Koordinate, bei welcher der Text angezeigt werden soll. 0 ist oben.
     * @param fontSize Die Schriftgröße, mindestens 5.
     * @param bold     Die Schriftart. Bei true wird die Schrift fettgedruckt.
     * @param color    Die Farbe, in der der Text angezeigt werden soll.
     * @param rotation Die Rotation der Schrift in Grad um den Mittelpunkt.
     * @param fontName Name des zu verwendenden Fonts. Das Font-File muss in einem Verzeichnis "src/resources" liegen
     *                 und auf ".ttf" enden.
     */
    public void addTextToCanvas(String text, double x, double y, double fontSize, boolean bold, Color color, double rotation, String fontName) {
        BufferedImage image = swingAdapter.createImageFromText(text, fontSize, color, bold, fontName);
        addImageToCanvasIfVisible(image, x, y, rotation);
    }

    /**
     * Erzeugt eine Grafik aus einer Datei. Die Datei muss im Verzeichnis "src/resources" liegen. Bitte den Namen der
     * Datei ohne Verzeichnisnamen angeben, z.B.<code>"raumschiff.png"</code>. Der Dateiname darf nur aus
     * Kleinbuchstaben bestehen.
     * <p>
     * Die Grafik wird auf die Leinwand (Canvas) übertragen, ohne die bisherigen Inhalte zu löschen.
     * <p>
     * Koordinaten werden ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten können verwendet
     * werden, um Grafiken teilweise anzuzeigen.
     * <p>
     * Die Größe der Grafik mit dem Parameter <code>scaleFactor</code> festgelegt werden. Beispielsweise bedeutet
     * <code>scaleFactor = 1</code>, dass das Bild in Originalgröße angezeigt wird.
     * <p>
     * Die Grafik kann mit einer Rotation dargestellt werden, dabei wird um den Mittelpunkt der Grafik rotiert. Eine
     * Rotation um 0° stellt das Bild ohne Rotation dar, bei 180° steht das Bild auf dem Kopf.
     *
     * @param imageFile        Der Dateiname des Bildes.
     * @param x                x-Koordinate, bei welcher das Bild angezeigt werden soll. 0 ist links.
     * @param y                y-Koordinate, bei welcher das Bild angezeigt werden soll. 0 ist oben.
     * @param imageScaleFactor Skalierungsfaktor des Bildes.
     * @param rotation         Die Rotation des Bildes in Grad um den Mittelpunkt.
     */
    public void addImageToCanvas(String imageFile, double x, double y, double imageScaleFactor, double rotation) {
        BufferedImage image = swingAdapter.createImageFromFile(imageFile, imageScaleFactor);
        addImageToCanvasIfVisible(image, x, y, rotation);
    }

    private void addImageToCanvasIfVisible(BufferedImage image, double x, double y, double rotation) {
        int xInt = (int) Math.ceil(x * swingAdapter.paintingPanel.windowsScaleFactor * swingAdapter.paintingPanel.panelScaleFactor);
        int yInt = (int) Math.ceil(y * swingAdapter.paintingPanel.windowsScaleFactor * swingAdapter.paintingPanel.panelScaleFactor);
        int width = image.getWidth();
        int height = image.getHeight();
        int diagonale = (int) Math.ceil(Math.sqrt(width * width + height * height));
        if (swingAdapter.paintingPanel.scaledBounds.intersects(new java.awt.Rectangle(xInt, yInt, diagonale, diagonale))) {
            canvas.addImageToCanvas(image, xInt, yInt, rotation);
        } else {
            statistic.invisiblePrintObjects++;
        }
    }

    /**
     * Diese Methode kann ein farbiges Oval auf die Leinwand (Canvas) zeichnen, ohne die bisherigen Inhalte zu löschen.
     * <p>
     * Die Koordinaten werden wie folgt ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten
     * können verwendet werden, um Ovale teilweise anzuzeigen.
     *
     * @param xCenter    x-Koordinate des Mittelpunkts des Ovals. 0 ist links.
     * @param yCenter    y-Koordinate des Mittelpunkts des Ovals. 0 ist oben.
     * @param width      Breite des Ovals.
     * @param height     Höhe des Ovals.
     * @param lineWeight Die Linienstärke des Ovals.
     * @param filled     Legt fest, ob das Oval gefüllt werden soll oder nicht.
     * @param color      Die Farbe des Ovals.
     */
    public void addOvalToCanvas(double xCenter, double yCenter, double width, double height, double lineWeight, boolean filled, Color color) {
        if (lineWeight < 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height have to be positive numbers and lineWeight can't be negative.");
        }
        int xInt = (int) Math.ceil(xCenter - width / 2);
        int yInt = (int) Math.ceil(yCenter - height / 2);
        int widthInt = (int) Math.ceil(width);
        int heightInt = (int) Math.ceil(height);
        if (rectangleIntersectsGameViewBounds(xInt, yInt, widthInt, heightInt, lineWeight)) {
            canvas.addOvalToCanvas((int) Math.ceil(xCenter), (int) Math.ceil(yCenter), widthInt, heightInt, (int) Math.ceil(lineWeight), filled, color);
        }
    }

    private boolean rectangleIntersectsGameViewBounds(int x, int y, int width, int height, double lineWeight) {
        int halfLineWeight = (int) Math.round(lineWeight / 2);
        java.awt.Rectangle rect = new java.awt.Rectangle(x - halfLineWeight, y - halfLineWeight, width + halfLineWeight, height + halfLineWeight);
        boolean intersects = rect.intersects(swingAdapter.paintingPanel.bounds);
        if (!intersects) {
            statistic.invisiblePrintObjects++;
        }
        return intersects;
    }

    /**
     * Diese Methode kann ein farbiges Rechteck auf die Leinwand (Canvas) zeichnen, ohne die bisherigen Inhalte zu
     * löschen.
     * <p>
     * Die Koordinaten werden wie folgt ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten
     * können verwendet werden, um Rechtecke teilweise anzuzeigen.
     *
     * @param x          x-Koordinate der linken oberen Ecke des Rechtecks. 0 ist links.
     * @param y          y-Koordinate der linken oberen Ecke des Rechtecks. 0 ist oben.
     * @param width      Breite des Rechtecks.
     * @param height     Höhe des Rechtecks.
     * @param lineWeight Die Linienstärke.
     * @param filled     Legt fest, ob das Rechteck gefüllt werden soll oder nicht.
     * @param color      Die Farbe des Rechtecks.
     */
    public void addRectangleToCanvas(double x, double y, double width, double height, double lineWeight, boolean filled, Color color) {
        if (lineWeight < 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height have to be positive numbers and lineWeight can't be negative.");
        }
        int xInt = (int) Math.ceil(x);
        int yInt = (int) Math.ceil(y);
        int widthInt = (int) Math.ceil(width);
        int heightInt = (int) Math.ceil(height);
        if (rectangleIntersectsGameViewBounds(xInt, yInt, widthInt, heightInt, lineWeight)) {
            canvas.addRectangleToCanvas(xInt, yInt, widthInt, heightInt, (int) Math.round(lineWeight), filled, color);
        }
    }

    /**
     * Diese Methode kann eine farbige Linie auf die Leinwand (Canvas) zeichnen, ohne die bisherigen Inhalte zu
     * löschen.
     * <p>
     * Die Koordinaten werden wie folgt ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten
     * können verwendet werden, um Linien teilweise anzuzeigen.
     *
     * @param xStart     x-Koordinate des Startpunkts der Linie. 0 ist links.
     * @param yStart     y-Koordinate des Startpunkts der Linie. 0 ist oben.
     * @param xEnd       x-Koordinate des Endpunkts der Linie. 0 ist links.
     * @param yEnd       y-Koordinate des Endpunkts der Linie. 0 ist oben.
     * @param lineWeight Die Linienstärke.
     * @param color      Die Farbe der Linie.
     */
    public void addLineToCanvas(double xStart, double yStart, double xEnd, double yEnd, double lineWeight, Color color) {
        if (lineWeight < 0) {
            throw new IllegalArgumentException(Tools.NEGATIVE_LINEWEIGHT);
        }
        int xStartInt = (int) Math.round(xStart);
        int yStartInt = (int) Math.round(yStart);
        int xEndInt = (int) Math.round(xEnd);
        int yEndInt = (int) Math.round(yEnd);
        int[] xs = new int[]{xStartInt, xEndInt};
        int[] ys = new int[]{yStartInt, yEndInt};
        if (lineIntersectsGameViewBounds(xs, ys, lineWeight)) {
            canvas.addLineToCanvas(xStartInt, yStartInt, xEndInt, yEndInt, (int) Math.round(lineWeight), color);
        }
    }

    private boolean lineIntersectsGameViewBounds(int[] xs, int[] ys, double lineWeight) {
        IntSummaryStatistics statX = Arrays.stream(xs).summaryStatistics();
        IntSummaryStatistics statY = Arrays.stream(ys).summaryStatistics();
        return rectangleIntersectsGameViewBounds(statX.getMin(), statY.getMin(), statX.getMax() - statX.getMin(), statY.getMax() - statY.getMin(), lineWeight);
    }

    /**
     * Diese Methode kann eine farbige Poly-Linie (eine Linie zwischen mehreren Punkten) auf die Leinwand (Canvas)
     * zeichnen, ohne die bisherigen Inhalte zu löschen. Dazu müssen alle Punkte der Poly-Linie angegeben werden.
     * <p>
     * Die Koordinaten werden wie folgt ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten
     * können verwendet werden, um die Linien teilweise anzuzeigen.
     *
     * @param xCoordinates Die x-Koordinaten der Punkte der Poly-Linie.
     * @param yCoordinates Die y-Koordinaten der Punkte der Poly-Linie.
     * @param lineWeight   Die Linienstärke.
     * @param color        Die Farbe der Poly-Linie.
     */
    public void addPolyLineToCanvas(double[] xCoordinates, double[] yCoordinates, double lineWeight, Color color) {
        if (lineWeight < 0) {
            throw new IllegalArgumentException(Tools.NEGATIVE_LINEWEIGHT);
        }
        int[] xs = convertDoubleToIntArray(xCoordinates);
        int[] ys = convertDoubleToIntArray(yCoordinates);
        if (lineIntersectsGameViewBounds(xs, ys, lineWeight)) {
            canvas.addPolyLineToCanvas(xs, ys, (int) Math.round(lineWeight), color);
        }
    }

    /**
     * Diese Methode kann ein farbiges Polygon auf die Leinwand (Canvas) zeichnen, ohne die bisherigen Inhalte zu
     * löschen. Dazu müssen alle Punkte des Polygons angegeben werden. Der Letzte angegebene Punkt wird mit dem ersten
     * Punkt des Polygons verbunden.
     * <p>
     * Die Koordinaten werden wie folgt ausgewertet: (0, 0) ist links oben {@link #GameView()}. Negative Koordinaten
     * können verwendet werden, um das Polygon teilweise anzuzeigen.
     *
     * @param xCoordinates Die x-Koordinaten der Punkte des Polygons.
     * @param yCoordinates Die y-Koordinaten der Punkte des Polygons.
     * @param lineWeight   Die Linienstärke.
     * @param filled       Legt fest, ob das Polygon gefüllt werden soll oder nicht.
     * @param color        Die Farbe des Polygons.
     */
    public void addPolygonToCanvas(double[] xCoordinates, double[] yCoordinates, double lineWeight, boolean filled, Color color) {
        if (lineWeight < 0) {
            throw new IllegalArgumentException(Tools.NEGATIVE_LINEWEIGHT);
        }
        int[] xs = convertDoubleToIntArray(xCoordinates);
        int[] ys = convertDoubleToIntArray(yCoordinates);
        if (lineIntersectsGameViewBounds(xs, ys, lineWeight)) {
            canvas.addPolygonToCanvas(xs, ys, (int) Math.round(lineWeight), filled, color);
        }
    }

    private int[] convertDoubleToIntArray(double[] original) {
        int[] converted = new int[original.length];
        for (int i = 0; i < converted.length; i++) {
            converted[i] = (int) Math.round(original[i]);
        }
        return converted;
    }

    /**
     * Legt fest, ob die Maus im Fenster benutzt werden soll. Falls sie nicht benutzt wird, wird der Cursor der Maus auf
     * die Default-Ansicht zurückgesetzt und die Maus wird ausgeblendet. Falls sie benutzt wird, werden Maus-Ereignisse
     * erzeugt, die verwendet werden können. Die Standardeinstellung ist <code>false</code>.
     *
     * @param useMouse Legt fest, ob die Maus im Fenster benutzt werden soll.
     */
    public void useMouse(boolean useMouse) {
        mouse.useMouse(useMouse);
    }

    /**
     * Liest den Inhalt einer Textdatei als String ein. Die Datei muss im Verzeichnis "src/resources" liegen. Bitte den
     * Namen der Datei ohne Verzeichnisnamen angeben, z.B.<code>"raumschiff.txt"</code>. Der Dateiname darf nur aus
     * Kleinbuchstaben bestehen.
     * <p>
     * Der Inhalt der Datei wird als ein einziger String zurückgegeben. Wenn die Datei nicht existiert oder nicht
     * gelesen werden kann, wird eine Ausnahme geworfen.
     * <p>
     * Achtung der Festplattenzugriff ist langsam, sie sollten diese Methode am besten nur ein einziges Mal aufrufen.
     *
     * @param fileName Der Name der Datei, die gelesen werden soll. Die Textdatei muss sich im Verzeichnis "resources"
     *                 befinden.
     * @return Ein String, der den Inhalt der Textdatei darstellt. Gibt "Datei nicht gefunden" zurück, wenn die Datei im
     * Ressourcenverzeichnis nicht existiert.
     * @throws IllegalArgumentException Falls die Datei nicht gelesen werden kann oder andere Fehler auftreten.
     */
    public String readTextFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("resources/" + fileName)) {
            if (inputStream == null) {
                return "Datei nicht gefunden";
            } else {
                try (InputStreamReader streamReader = new InputStreamReader(inputStream);
                     BufferedReader reader = new BufferedReader(streamReader)) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Die Textdatei \"" + fileName + "\" konnte nicht gefunden werden!");
        }
    }

    /**
     * Gibt zurück, ob die Maus eingeschaltet ist.
     *
     * @return true, falls die Maus eingeschaltet ist.
     */
    public boolean mouseEnabled() {
        return mouse.useMouse;
    }

    /**
     * Legt ein neues Symbol für den Maus-Cursor fest. Die Bild-Datei muss im Verzeichnis "src/resources" liegen. Bitte
     * den Namen der Datei ohne Verzeichnisnamen angeben, z.B. <code>changeMouseCursor("cursor.png", false)</code>.
     *
     * @param cursorImageFileName Name der Bild-Datei. Die Bild-Datei muss in einem Verzeichnis "src/resources" liegen.
     * @param centered            Gibt an, ob der Hotspot des Cursors in der Mitte des Symbols oder oben links liegen
     *                            soll.
     */
    public void changeMouseCursor(String cursorImageFileName, boolean centered) {
        mouse.setMouseCursor(cursorImageFileName, centered);
    }

    /**
     * Der Maus-Cursor wird auf das Standard-Icon zurückgesetzt.
     */
    public void resetMouseCursor() {
        mouse.setStandardMouseCursor();
    }

    /**
     * Falls die Maus mit {@link #useMouse(boolean)} aktiviert wurde, liefert diese Methode alle Mausereignisse, die
     * seit dem letzten Aufruf dieser Methode aufgelaufen sind als Array zurück. Es werden maximal die neuesten 25
     * Ereignisse zurückgegeben, alte Ereignisse werden gelöscht. Diese Methode ist geeignet um die Texteingaben vom
     * Benutzer zu realisieren.
     * <p>
     * Das Array enthält Ereignisse vom Typ {@link MouseEvent}. Das Ereignis enthält Koordinaten auf der Leinwand
     * (Canvas) und die Information, ob die Maus gedrückt, losgelassen, geklickt oder nur bewegt wurde. Um
     * festzustellen, wie die Maus betätigt wurde, kann der Typ des Ereignisses mit {@link MouseEvent#getID()} abgefragt
     * werden. Folgende Konstanten werden weitergeleitet:
     * <br>
     * <code>MouseEvent.MOUSE_PRESSED</code> <br>
     * <code>MouseEvent.MOUSE_RELEASED</code> <br>
     * <code>MouseEvent.MOUSE_CLICKED</code> <br>
     * <code>MouseEvent.MOUSE_MOVED</code> <br>
     * <br>
     * Die Fensterkoordinaten können mit den Methoden<br> {@link MouseEvent#getX()} = X-Koordinate<br>
     * {@link MouseEvent#getY()} = Y-Koordinate<br> abgerufen werden, um X und Y-Koordinate des Ereignisses zu
     * bestimmen.<br>
     * <br>
     * Beispiel zur Erkennung einer gedrückten Maustaste:<br>
     *
     * <pre>
     * <code>
     * <br>
     * package demo;
     *
     * import java.awt.event.MouseEvent;
     *
     * public class MouseEventTest {
     *   GameView gameView;
     *
     *   public MouseEventTest() {
     *     gameView = new GameView();
     *     gameView.useMouse(true);
     *     loop();
     *   }
     *
     *   public void loop() {
     *     int x = 0;
     *     int y = 0;
     *     while(true) {
     *       MouseEvent[] mouseEvents = gameView.mouseEvents();
     *       for (MouseEvent mouseEvent : mouseEvents) {
     *         if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
     *           x = mouseEvent.getX();
     *           y = mouseEvent.getY();
     *         }
     *       }
     *       gameView.addTextToCanvas("X=" + x + " Y=" + y, x, y, 12, Color.WHITE);
     *       gameView.printCanvas();
     *     }
     *   }
     * }
     * </code>
     * </pre>
     * <p>
     * Mit {@link MouseEvent#getButton()} ()} lässt sich ermitteln, welche Maustaste betätigt wurde (links, rechts oder
     * die Mitte).
     *
     * @return Alle Mausereignisse seit dem letzten Aufruf dieser Methode.
     * @see MouseEvent
     */
    public MouseEvent[] mouseEvents() {
        return mouse.pollMouseEvents();
    }

    /**
     * Falls die Maus mit {@link #useMouse(boolean)} aktiviert wurde, liefert diese Methode alle gerade im Moment
     * gedrückten Tasten als <code>KeyCode</code> der Klasse {@link KeyEvent} als Array zurück. Es handelt sich dabei um
     * Ganzzahlen vom Typ <code>Integer</code>. Die Tasten sind in der Reihenfolge enthalten, in der sie gedrückt
     * wurden. Diese Methode ist geeignet um die Steuerung von Spielfiguren zu realisieren.
     * <p>
     * Ein Abgleich der KeyCodes kann über Konstanten der Klasse {@link KeyEvent} erfolgen. Beispielsweise kann die
     * Leertaste mithilfe der Konstante {@link KeyEvent#VK_SPACE} abgeglichen werden.
     * <pre>
     * <code>
     * <br>
     * package demo;
     *
     * import java.awt.event.KeyEvent;
     *
     * public class PressedKeys {
     *   GameView gameView;
     *
     *   public PressedKeys() {
     *     gameView = new GameView();
     *     loop();
     *   }
     *
     *   private void loop() {
     *     while (true) {
     *       Integer[] pressedKeys = gameView.keyCodesOfCurrentlyPressedKeys();
     *       String result = "";
     *       for (int keyCode : pressedKeys) {
     *         if (keyCode == KeyEvent.VK_UP) {
     *           result += "UP\n";
     *         } else if (keyCode == KeyEvent.VK_DOWN) {
     *           result += "Down\n";
     *         } else if (keyCode == KeyEvent.VK_LEFT) {
     *           result += "Left\n";
     *         } else if (keyCode == KeyEvent.VK_RIGHT) {
     *           result += "Right\n";
     *         } else if (keyCode == KeyEvent.VK_SPACE) {
     *           result += "Space\n";
     *         }
     *       }
     *       gameView.print(result, 6);
     *     }
     *   }
     * }
     *
     * </code>
     * </pre>
     *
     * @return Alle gerade gedrückten Tasten als <code>KeyCode</code> in einem Array.
     * @see KeyEvent
     */
    public Integer[] keyCodesOfCurrentlyPressedKeys() {
        return keyboard.getKeyCodesOfCurrentlyPressedKeys();
    }

    /**
     * Liefert alle Tastenereignisse, die seit dem letzten Aufruf dieser Methode aufgelaufen sind als Array zurück. Es
     * werden maximal die neuesten 25 Ereignisse zurückgegeben, alte Ereignisse werden gelöscht.
     * <p>
     * Das Array enthält Ereignisse vom Typ {@link KeyEvent}. Der Typ des Events ist entweder<br>
     * <code>KeyEvent.KEY_PRESSED</code> (Taste wurde gedrückt),<br>
     * <code>KeyEvent.KEY_RELEASED</code> (Taste wurde losgelassen)<br>
     * oder <code>KeyEvent.KEY_TYPED</code>(Taste wurde getippt, funktioniert nur für sichtbare Zeichen).
     * <p>
     * Sichtbare Zeichen lassen sich mit der Methode {@link KeyEvent#getKeyChar()} direkt auslesen.
     * <p>
     * Bei Tastenereignissen gibt es die sogenannte Anschlagverzögerung. Das bedeutet, dass wenn man eine Taste gedrückt
     * hält, dann wird die Taste einmal ausgelöst, dann folgt eine kurze Pause, dann folgt eine schnelle Wiederholung
     * des Tastendrucks. Falls dieses Verhalten nicht gewünscht ist (z.B. bei der Steuerung von Spielfiguren), sollte
     * stattdessen die Methode {@link #keyCodesOfCurrentlyPressedKeys()} verwendet werden.
     *
     * <pre>
     * <code>
     * <br>
     * package demo;
     *
     * import java.awt.event.KeyEvent;
     *
     * public class KeyEventTest {
     *   GameView gameView;
     *
     *   public KeyEventTest() {
     *     gameView = new GameView();
     *     loop();
     *   }
     *
     *   public void loop() {
     *     while (true) {
     *       KeyEvent[] keyEvents = gameView.keyEvents();
     *       for (KeyEvent keyEvent : keyEvents) {
     *         if (keyEvent.getID() == KeyEvent.KEY_TYPED) {
     *           gameView.print("Taste: " + keyEvent.getKeyChar(), 6);
     *         }
     *       }
     *     }
     *   }
     * }
     * </code>
     * </pre>
     * <br>
     *
     * @return Alle <code>KeyEvent</code> Ereignisse seit dem letzten Aufruf dieser Methode.
     * @see KeyEvent
     * @see #keyCodesOfCurrentlyPressedKeys()
     */
    public KeyEvent[] keyEvents() {
        return keyboard.pollKeyEvents();
    }

    /**
     * Spielt einen Sound ab (eine .wav-Datei). Die Sound-Datei muss in einem Verzeichnis "src/resources" liegen. Bitte
     * den Namen der Datei ohne Verzeichnisnamen angeben, z.B. <code>playSound("boom.wav", false)</code>. Der Dateiname
     * darf nur aus Kleinbuchstaben bestehen.
     * <p>
     * Der Sound beendet sich selbst, sobald er fertig abgespielt wurde. Der Parameter "replay" kann genutzt werden, um
     * den Sound endlos zu wiederholen. Mit der Methode {@link #stopSound(int)} kann ein Sound frühzeitig beendet
     * werden. Mit der Methode {@link #stopAllSounds()} können alle laufenden Sounds beendet werden. Achten Sie auf
     * Groß- und Kleinschreibung beim Soundfile!
     *
     * @param soundFile Name der Sound-Datei. Die Sound-Datei muss in einem Verzeichnis "src/resources" liegen und auf
     *                  ".wav" enden.
     * @param replay    Legt fest, ob der Sound endlos wiederholt werden soll.
     * @return Die eindeutige Identifikationsnummer des Soundfiles wird zurückgegeben. Diese Nummer kann genutzt werden
     * um mit der Methode {@link #stopSound(int)} das Abspielen des Sounds zu beenden.
     */
    public int playSound(String soundFile, boolean replay) {
        return this.sound.playSound(soundFile, replay);
    }

    /**
     * Stoppt den Sound mit der angegebenen Nummer. Falls der Sound schon gestoppt wurde, passiert nichts.
     *
     * @param id Die eindeutige Identifikationsnummer des Soundfiles, das gestoppt werden soll.
     */
    public void stopSound(int id) {
        sound.stopSound(id);
    }

    /**
     * Stoppt alle gerade spielenden Sounds.
     */
    public void stopAllSounds() {
        sound.stopAllSounds();
    }

    /**
     * Schließt das GameView-Fenster.
     */
    public void closeWindow() {
        gameViewClosed = true;
        swingAdapter.closeWindow(false);
        instances--;
    }

    /**
     * Liefert die Zeit in Millisekunden, die seit dem Start von GameView verstrichen sind.
     *
     * @return Zeit in Millisekunden.
     */
    public int gameTimeInMilliseconds() {
        return (int) (System.currentTimeMillis() - startTimeInMilliseconds);
    }

    /**
     * Ein Timer liefert einen zeitgesteuerten booleschen Wert. Der Timer ist so lange false, bis er abläuft. Wenn der
     * Timer abgelaufen ist, liefert er ein einziges Mal true zurück. Danach wird der Timer auf seinen Ausgangszustand
     * zurückgesetzt.
     * <br><br>
     * Beispielhafte Anwendung eines Timers der nach 300 Millisekunden true wird:
     * <pre>
     * <code>
     * if (gameView.timer(300, this)) {
     *       playersExtrapower = false;
     *   }
     * </code>
     * </pre>
     * Ein Timer muss durch das zugehörige Objekt eindeutig identifizierbar gemacht werden.
     *
     * @param milliseconds Anzahl der Millisekunden, nach denen der Timer abläuft.
     * @param id           Das aufrufende Objekt zu dem dieser Timer gehört.
     * @return True, wenn der Timer gerade abgelaufen ist, sonst false.
     */
    public boolean timer(int milliseconds, Object id) {
        if (milliseconds < 1) {
            return true;
        }
        int hash = Thread.currentThread().getStackTrace()[2].getLineNumber() + System.identityHashCode(id);
        Long dueTime = timers.get(hash);
        if (dueTime == null) {
            timers.put(hash, System.currentTimeMillis() + milliseconds);
            return false;
        } else {
            if (System.currentTimeMillis() >= dueTime) {
                timers.remove(hash);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Alle laufenden Timer des übergebenen Objekts werden abgebrochen und die Timer werden auf ihren Ausgangszustand
     * zurückgesetzt.
     *
     * @param id Das Objekt, dessen Timer zurückgesetzt werden sollen.
     */
    public void resetTimers(Object id) {
        int objectHash = System.identityHashCode(id);
        for (int i = 0; i < 2000; i++) {
            timers.remove(objectHash + i);
        }
    }

    /**
     * Zeigt ein Fenster an, in dem Text ausgegeben werden kann. Nutzen Sie die Methode
     * {@link #print(String, int, boolean, double)} um Text auszugeben. Mit dieser Methode kann das Fenster auch wieder
     * geschlossen werden.
     *
     * @param show True um das Fenster zu öffnen, false um ein geöffnetes Fenster zu schließen.
     */
    public static void showGameView(boolean show) {
        if (show) {
            if (instance != null) {
                throw new IllegalStateException("GameView is already shown.");
            }
            instances++;
            instance = new GameView() {
                @Override
                public void initialize() {
                }

                @Override
                public void gameLoop() {
                }
            };
            instance.changeBackgroundColor(Color.BLACK);
            instance.swingAdapter.showWindowAndCreateBufferStrategy();
        } else {
            if (instance == null) {
                throw new IllegalStateException("GameView is already closed.");
            }
            instance.closeWindow();
            instance = null;
        }
    }

    /**
     * Gibt den übergebenen Text direkt im Fenster aus. Es muss die Schriftgröße und die Schriftart gewählt werden. Das
     * Fenster muss bereits mit der Methode {@link #showGameView(boolean)} geöffnet worden sein.
     * <p>
     * Zwischen zwei Aufrufen dieser Methode wird automatisch eine Pause eingefügt. Das führt zu einer Darstellung von
     * höchstens 60 Bildern pro Sekunde.
     *
     * @param string   Der anzuzeigende String.
     * @param fontSize Die Schriftgröße.
     * @param bold     Die Schriftart. Bei true wird die Schrift fettgedruckt.
     */
    public static void print(String string, int fontSize, boolean bold, double lineSpacing) {
        checkStaticUsage();
        if (lineSpacing > 0 && lineSpacing < 1) {
            String[] lines = string.split("\\R");
            double spacing = fontSize * lineSpacing;
            for (int i = 0; i < lines.length; i++) {
                if (!lines[i].isEmpty()) {
                    instance.addTextToCanvas(lines[i], 0, spacing * i - (spacing / 2 - 1), fontSize, bold, Color.WHITE, 0);
                }
            }
        } else {
            instance.addTextToCanvas(string, 0, 0, fontSize, bold, Color.WHITE, 0);
        }
        instance.swingAdapter.paintingPanel.paintImageToWindow(instance.canvas.printObjects, instance.canvas.backgroundColor);
        instance.canvas.printObjects.clear();
    }

    private static void checkStaticUsage() {
        if (instance == null) {
            throw new IllegalStateException("""
                    The methods print(...), printTestScreen() and keyPressed(...) can only be used when GameView is
                    used in a static way.
                                        
                    GameView needs to be shown by calling GameView.showGameView(true), before text can be printed.""");
        }
    }

    /**
     * Gibt einen Test-String direkt im Fenster aus. Es muss die Schriftgröße und die Schriftart gewählt werden. Das
     * Fenster muss bereits mit der Methode {@link #showGameView(boolean)} geöffnet worden sein.
     * <p>
     * Zwischen zwei Aufrufen dieser Methode wird automatisch eine Pause eingefügt. Das führt zu einer Darstellung von
     * höchstens 60 Bildern pro Sekunde.
     *
     * @param fontSize Die Schriftgröße.
     * @param bold     Die Schriftart. Bei true wird die Schrift fettgedruckt.
     */
    public static void printTestScreen(int fontSize, boolean bold, double lineSpacing) {
        checkStaticUsage();
        char[][] test = new char[999][999];
        for (int col = 1; col < test.length - 1; col++) {
            for (int lin = 1; lin < test[col].length - 1; lin++) {
                if (lin == 1) {
                    if (col % 2 == 0) {
                        test[col - 1][lin - 1] = getCharAt(0, col);
                        test[col - 1][lin] = getCharAt(1, col);
                        test[col - 1][lin + 1] = getCharAt(2, col);
                    }
                } else if (col == 1) {
                    if (lin % 2 == 0) {
                        test[col - 1][lin - 1] = getCharAt(0, lin);
                        test[col][lin - 1] = getCharAt(1, lin);
                        test[col + 1][lin - 1] = getCharAt(2, lin);
                    }
                }
            }
        }
        print(convert2DCharArrayToString(test), fontSize, bold, lineSpacing);
    }

    private static char getCharAt(int i, int number) {
        String numberString = String.valueOf(number);
        if (i == 0) {
            return numberString.charAt(0);
        } else if (i == 1) {
            if (numberString.length() > 1) {
                return numberString.charAt(1);
            } else {
                return ' ';
            }
        } else if (i == 2) {
            if (numberString.length() > 2) {
                return numberString.charAt(2);
            } else {
                return ' ';
            }
        } else {
            return 'X';
        }
    }

    private static String convert2DCharArrayToString(char[][] charArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] chars : charArray) {
            for (char aChar : chars) {
                if (aChar != '\0') {
                    stringBuilder.append(aChar);
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * Zeigt an, ob die übergebene Taste gerade gedrückt wird. Das Fenster muss bereits mit der Methode
     * {@link #showGameView(boolean)} geöffnet worden sein.
     * <p>
     *
     * @param desiredKeyCode Ein KeyCode von <code>java.awt.event.KeyEvent</code>, z.B. <code>KeyEvent.VK_A</code> für
     *                       den Buchstaben A.
     * @return True, falls diese Taste gerade gedrückt wird.
     */
    public static boolean keyPressed(int desiredKeyCode) {
        checkStaticUsage();
        return instance.keyboard.keyCodesOfCurrentlyPressedKeys.contains(desiredKeyCode);
    }

    /**
     * Es wird eine Wartezeit eingefügt. Das Fenster muss bereits mit der Methode {@link #showGameView(boolean)}
     * geöffnet worden sein.
     * <p>
     *
     * @param milliseconds Die Anzahl von Millisekunden der Wartezeit.
     */
    public static void sleep(int milliseconds) {
        checkStaticUsage();
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Diese Methode kann die Versionsnummer von GameView verifizieren.
     *
     * @param versionString Die mindestens erwartete Versionsnummer.
     * @return True, falls GameView mindestens die erwartete Versionsnummer hat.
     */
    public static boolean versionNumberIsAtLeast(String versionString) {
        return !Version.isSmallerThan(versionString);
    }

    private abstract static class PrintObject {
        protected enum PrintType {
            OVAL, RECTANGLE, IMAGE_OBJECT, POLYGON, POLYLINE, LINE
        }

        PrintType type;
        protected final int x;
        protected final int y;
        protected final Color color;

        protected PrintObject(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    private static class Oval extends PrintObject {
        private final int width;
        private final int height;
        private final int lineWeight;
        private final boolean filled;

        private Oval(int xCenter, int yCenter, int width, int height, int lineWeight, boolean filled, Color color) {
            super(xCenter, yCenter, color);
            this.type = PrintType.OVAL;
            this.width = width;
            this.height = height;
            this.lineWeight = lineWeight;
            this.filled = filled;
        }
    }

    private static class Rectangle extends PrintObject {
        private final int width;
        private final int height;
        private final int lineWeight;
        private final boolean filled;

        private Rectangle(int x, int y, int width, int height, int lineWeight, boolean filled, Color color) {
            super(x, y, color);
            this.type = PrintType.RECTANGLE;
            this.width = width;
            this.height = height;
            this.lineWeight = lineWeight;
            this.filled = filled;
        }
    }

    private static class Line extends PrintObject {
        private final int xEnd;
        private final int yEnd;
        private final int lineWeight;

        private Line(int xStart, int yStart, int xEnd, int yEnd, int lineWeight, Color color) {
            super(xStart, yStart, color);
            this.type = PrintType.LINE;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
            this.lineWeight = lineWeight;
        }
    }

    private static class Polygon extends PrintObject {
        private final int[] xCoordinates;
        private final int[] yCoordinates;
        private final int lineWeight;
        private final boolean filled;

        private Polygon(int[] xCoordinates, int[] yCoordinates, int lineWeight, boolean filled, Color color) {
            super(xCoordinates[0], yCoordinates[0], color);
            this.type = PrintType.POLYGON;
            this.xCoordinates = xCoordinates;
            this.yCoordinates = yCoordinates;
            this.lineWeight = lineWeight;
            this.filled = filled;
        }
    }

    private static class PolyLine extends PrintObject {
        private final int[] xCoordinates;
        private final int[] yCoordinates;
        private final int lineWeight;

        private PolyLine(int[] xCoordinates, int[] yCoordinates, int lineWeight, Color color) {
            super(xCoordinates[0], yCoordinates[0], color);
            this.type = PrintType.POLYLINE;
            this.xCoordinates = xCoordinates;
            this.yCoordinates = yCoordinates;
            this.lineWeight = lineWeight;
        }
    }

    private static class ImageObject extends PrintObject {
        private final BufferedImage image;
        private final double rotation;

        private ImageObject(int x, int y, BufferedImage image, double rotation) {
            super(x, y, Color.BLACK);
            this.type = PrintType.IMAGE_OBJECT;
            this.image = image;
            this.rotation = rotation;
        }
    }

    private static class Canvas {
        private Color backgroundColor;
        private ArrayList<PrintObject> printObjects;

        private Canvas() {
            this.backgroundColor = Color.black;
            this.printObjects = new ArrayList<>(1500);
        }

        private void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        private void addImageToCanvas(BufferedImage image, int x, int y, double rotation) {
            printObjects.add(new ImageObject(x, y, image, rotation));
        }

        private void addRectangleToCanvas(int x, int y, int width, int height, int lineWeight, boolean filled, Color color) {
            printObjects.add(new Rectangle(x, y, width, height, lineWeight, filled, color));
        }

        private void addLineToCanvas(int xStart, int yStart, int xEnd, int yEnd, int lineWeight, Color color) {
            printObjects.add(new Line(xStart, yStart, xEnd, yEnd, lineWeight, color));
        }

        private void addOvalToCanvas(int xCenter, int yCenter, int width, int height, int lineWeight, boolean filled, Color color) {
            printObjects.add(new Oval(xCenter, yCenter, width, height, lineWeight, filled, color));
        }

        private void addPolygonToCanvas(int[] xCoordinates, int[] yCoordinates, int lineWeight, boolean filled, Color color) {
            if (xCoordinates.length != yCoordinates.length) {
                throw new InputMismatchException("Die Anzahl der X- und Y-Koordinaten ist nicht gleich!");
            }
            printObjects.add(new Polygon(xCoordinates, yCoordinates, lineWeight, filled, color));
        }

        private void addPolyLineToCanvas(int[] xCoordinates, int[] yCoordinates, int lineWeight, Color color) {
            if (xCoordinates.length != yCoordinates.length) {
                throw new InputMismatchException("Die Anzahl der X- und Y-Koordinaten ist nicht gleich!");
            }
            printObjects.add(new PolyLine(xCoordinates, yCoordinates, lineWeight, color));
        }
    }

    private static class Frame extends JFrame {
        private Mouse mouse;
        private Keyboard keyboard;
        private final JPanel statusBar;
        private JLabel statusLabelLinks;

        private void registerListeners(Mouse mouse, Keyboard keyboard) {
            // Klassen
            this.mouse = mouse;
            this.keyboard = keyboard;
        }

        private Frame(PaintingPanel paintingPanel) {

            statusBar = new JPanel() {
                {
                    setLayout(new BorderLayout());
                    setBorder(BorderFactory.createRaisedBevelBorder());
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                    statusLabelLinks = new JLabel();
                    statusLabelLinks.setBackground(Color.WHITE);
                    statusLabelLinks.setForeground(Color.BLACK);
                    statusLabelLinks.setHorizontalAlignment(JLabel.LEFT);

                    JLabel statusLabelRechts = new JLabel(Version.getStatusSignature());
                    statusLabelRechts.setBackground(Color.WHITE);
                    statusLabelRechts.setForeground(Color.BLACK);
                    statusLabelRechts.setHorizontalAlignment(JLabel.RIGHT);
                    add(statusLabelLinks, BorderLayout.WEST);
                    add(statusLabelRechts, BorderLayout.EAST);
                }
            };

            JPanel center = new JPanel(new GridBagLayout());
            center.setBackground(Color.BLACK);
            center.add(paintingPanel);


            // Struktur
            paintingPanel.setPreferredSize(new Dimension(GameView.WIDTH, GameView.HEIGHT));
            JPanel paintingPanelAndStatusBar = new JPanel(new BorderLayout(0, 0));
            paintingPanelAndStatusBar.setBackground(Color.BLACK);
            paintingPanelAndStatusBar.add(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), BorderLayout.NORTH);
            paintingPanelAndStatusBar.add(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), BorderLayout.EAST);
            paintingPanelAndStatusBar.add(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), BorderLayout.WEST);
            paintingPanelAndStatusBar.add(center, BorderLayout.CENTER);
            paintingPanelAndStatusBar.add(statusBar, BorderLayout.SOUTH);
            add(paintingPanelAndStatusBar);

            // Eigenschaften
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle(Version.getStandardTitle());
            paintingPanel.setFocusable(false);
            setResizable(true);


            // Listeners
            KeyListener keyListener = new KeyListener() {

                @Override
                public void keyTyped(KeyEvent keyEvent) {
                    all(keyEvent);
                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {
                    all(keyEvent);
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                    all(keyEvent);
                }

                private void all(KeyEvent keyEvent) {
                    if (keyboard != null) {
                        keyboard.update(keyEvent);
                    }
                }
            };
            addKeyListener(keyListener);

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    all(mouseEvent);
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    all(mouseEvent);
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                    all(mouseEvent);
                }

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    all(mouseEvent);
                }

                private void all(MouseEvent mouseEvent) {
                    if (mouse != null) {
                        mouse.update(mouseEvent);
                    }
                }
            };
            paintingPanel.addMouseMotionListener(mouseAdapter);
            paintingPanel.addMouseListener(mouseAdapter);

            final javax.swing.Timer packTimer = new javax.swing.Timer(500, actionEvent -> {
                if (getExtendedState() != MAXIMIZED_BOTH) {
                    Point location = getLocation();
                    pack();
                    setLocation(location);
                }
            });
            packTimer.setRepeats(false);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    double scalingFactor = Math.min(paintingPanel.getParent().getWidth() * 1d / GameView.WIDTH, paintingPanel.getParent().getHeight() * 1d / GameView.HEIGHT);
                    int newWidth = (int) Math.round(GameView.WIDTH * scalingFactor);
                    int newHeight = (int) Math.round(GameView.HEIGHT * scalingFactor);
                    paintingPanel.setPreferredSize(new Dimension(newWidth, newHeight));
                    paintingPanel.setMinimumSize(new Dimension(newWidth, newHeight));
                    paintingPanel.setSize(new Dimension(newWidth, newHeight));
                    paintingPanel.setMaximumSize(new Dimension(newWidth, newHeight));
                    if (packTimer.isRunning()) {
                        packTimer.restart();
                    } else {
                        packTimer.start();
                    }
                    paintingPanel.updateScaleFactor();
                    revalidate();
                }
            });

            // Location und Ausgeben
            int newWidth = (int) (GameView.WIDTH * 1.3);
            int newHeight = (int) (GameView.HEIGHT * 1.3);
            paintingPanel.setPreferredSize(new Dimension(newWidth, newHeight));

            pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (getHeight() > screenSize.height || getWidth() > screenSize.width) {
                newWidth = GameView.WIDTH * 8 / 10;
                newHeight = GameView.HEIGHT * 8 / 10;
                paintingPanel.setPreferredSize(new Dimension(newWidth, newHeight));
                pack();
            }
            setLocationRelativeTo(null);
            if (instances > 0) {
                int offset = (instances - 1) * 25;
                int x = getLocation().x + offset;
                int y = getLocation().y + offset;
                setLocation(x, y);
            }
        }

        private JLabel getStatusLabelLinks() {
            return statusLabelLinks;
        }

        private JPanel getStatusBar() {
            return statusBar;
        }
    }

    private class Keyboard {
        private final ArrayBlockingQueue<KeyEvent> keyboardEvents;
        private final ArrayBlockingQueue<Integer> keyCodesOfCurrentlyPressedKeys;

        private static final int KEY_EVENT_BUFFER_SIZE = 25;

        private Keyboard() {
            keyboardEvents = new ArrayBlockingQueue<>(KEY_EVENT_BUFFER_SIZE, true);
            keyCodesOfCurrentlyPressedKeys = new ArrayBlockingQueue<>(10, true);
        }

        private void update(KeyEvent keyEvent) {
            if (keyboardEvents.size() == KEY_EVENT_BUFFER_SIZE) {
                keyboardEvents.remove();
            }
            keyboardEvents.add(keyEvent);
            if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                if (!keyCodesOfCurrentlyPressedKeys.contains(keyEvent.getKeyCode()))
                    keyCodesOfCurrentlyPressedKeys.add(keyEvent.getKeyCode());
            } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
                keyCodesOfCurrentlyPressedKeys.remove(keyEvent.getKeyCode());
                if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_R) {
                    statistic.showStatistics = !statistic.showStatistics;
                }
            }
        }

        private KeyEvent[] pollKeyEvents() {
            KeyEvent[] events = new KeyEvent[0];
            if (!keyboardEvents.isEmpty()) {
                events = keyboardEvents.toArray(events);
                keyboardEvents.clear();
            }
            return events;
        }

        private Integer[] getKeyCodesOfCurrentlyPressedKeys() {
            Integer[] keyCodes = new Integer[0];
            if (!keyCodesOfCurrentlyPressedKeys.isEmpty()) {
                keyCodes = keyCodesOfCurrentlyPressedKeys.toArray(keyCodes);
            }
            return keyCodes;
        }
    }

    private static class Mouse implements ActionListener {
        private final SwingAdapter swingAdapter;
        private boolean invisibleMouseCursor;
        private boolean invisibleMouseCursorMoved;
        private final javax.swing.Timer invisibleMouseTimer;
        private static final int MOUSE_EVENT_BUFFER_SIZE = 25;
        private final ArrayBlockingQueue<MouseEvent> mousePointerEvents;
        private boolean useMouse;

        private Mouse(SwingAdapter swingAdapter) {
            this.swingAdapter = swingAdapter;
            this.invisibleMouseCursor = false;
            this.invisibleMouseCursorMoved = true;
            this.mousePointerEvents = new ArrayBlockingQueue<>(MOUSE_EVENT_BUFFER_SIZE, true);
            this.invisibleMouseTimer = new javax.swing.Timer(500, this);
            setMouseInvisible();
        }

        private void setMouseInvisible() {
            this.useMouse = false;
            setInvisibleMouseCursor();
            if (!invisibleMouseTimer.isRunning()) {
                invisibleMouseTimer.start();
            }
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (invisibleMouseCursorMoved) {
                if (invisibleMouseCursor) {
                    setStandardMouseCursor();
                }
                invisibleMouseCursorMoved = false;
            } else {
                if (!invisibleMouseCursor) {
                    setInvisibleMouseCursor();
                }
            }
        }

        private void useMouse(boolean useMouse) {
            if (useMouse == this.useMouse) {
                return;
            }
            if (useMouse) {
                this.useMouse = true;
                setStandardMouseCursor();
                invisibleMouseTimer.stop();
            } else {
                setMouseInvisible();
            }
        }

        private void setStandardMouseCursor() {
            this.invisibleMouseCursor = false;
            swingAdapter.setStandardMouseCursor();
        }

        private void setMouseCursor(String cursorImageFileName, boolean centered) {
            this.invisibleMouseCursor = false;
            swingAdapter.setMouseCursor(cursorImageFileName, centered);
        }

        private void setInvisibleMouseCursor() {
            invisibleMouseCursor = true;
            swingAdapter.setInvisibleMouseCursor();
        }

        private void update(MouseEvent mouseEvent) {
            if (useMouse) {
                int mouseEventY = GameView.HEIGHT * mouseEvent.getY() / swingAdapter.getTextDisplaySize().height;
                int mouseEventX = GameView.WIDTH * mouseEvent.getX() / swingAdapter.getTextDisplaySize().width;
                MouseEvent fixedMouseEvent = new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiersEx(), mouseEventX, mouseEventY, mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
                if (mousePointerEvents.size() == MOUSE_EVENT_BUFFER_SIZE) {
                    mousePointerEvents.remove();
                }
                mousePointerEvents.add(fixedMouseEvent);
            } else {
                invisibleMouseCursorMoved = true;
            }
        }

        private MouseEvent[] pollMouseEvents() {
            MouseEvent[] events = new MouseEvent[0];
            if (!mousePointerEvents.isEmpty()) {
                events = mousePointerEvents.toArray(events);
                mousePointerEvents.clear();
            }
            return events;
        }
    }

    private static class Sound {
        private final ConcurrentHashMap<Integer, Clip> clips;
        private final ConcurrentHashMap<String, byte[]> storedSoundBytes;
        private final ConcurrentHashMap<String, Clip> storedClips;
        private static int soundCounter;

        private Sound() {
            clips = new ConcurrentHashMap<>();
            storedSoundBytes = new ConcurrentHashMap<>();
            storedClips = new ConcurrentHashMap<>();
            soundCounter = 0;
        }

        private void readSoundBytesAndStoreAsBytesAndClip(String soundFile) {
            if (!storedSoundBytes.containsKey(soundFile)) {
                try (InputStream stream = GameView.class.getResourceAsStream(Tools.RESOURCE_PREFIX + soundFile)) {
                    storedSoundBytes.put(soundFile, Objects.requireNonNull(stream).readAllBytes());
                } catch (IOException e) {
                    System.err.println("Soundfile \"" + soundFile + "\" konnte nicht gelesen werden!");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            byte[] soundBytes = storedSoundBytes.get(soundFile);
            Clip clip = retrieveNewOpenedClipFromSoundBytes(soundFile, soundBytes);
            addLineListener(soundFile.hashCode(), clip);
            storedClips.put(soundFile, clip);
        }

        private void addLineListener(Integer id, Clip clip) {
            clip.addLineListener(event -> {
                if (event.getType().equals(LineEvent.Type.STOP)) {
                    Clip finished = clips.remove(id);
                    if (!storedClips.contains(finished)) {
                        new Thread(() -> {
                            finished.flush();
                            finished.close();
                        }).start();
                    }
                }
            });
        }

        private Clip retrieveNewOpenedClipFromSoundBytes(String sound, byte[] soundBytes) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(soundBytes);
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream)) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
            } catch (Exception e) {
                System.err.println("Soundfile \"" + sound + "\" konnte nicht abgespielt werden!");
                e.printStackTrace();
                System.exit(1);
            }
            return null;
        }

        private int playSound(String soundFile, boolean replay) {
            Clip clip = storedClips.get(soundFile);
            if (clip == null) {
                Tools.checkLowerCaseFileNameCall(soundFile);
                // Longest runtime, but only once per sound.
                int id = soundFile.hashCode();
                new Thread(() -> readClipFromDiskAndPlay(id, soundFile, replay)).start();
                return id;
            } else {
                if (clip.isActive()) {
                    // Possibly long runtime, but only when sound is simultaneously started
                    int id = soundFile.hashCode() + (++soundCounter);
                    new Thread(() -> createNewClipFromStoredBytesAndPlay(id, soundFile, replay)).start();
                    return id;
                } else {
                    // Very short runtime.
                    int id = soundFile.hashCode();
                    clip.setFramePosition(0);
                    clip.setMicrosecondPosition(0);
                    play(id, clip, replay);
                    return id;
                }
            }
        }

        private void readClipFromDiskAndPlay(int id, String soundFile, boolean replay) {
            readSoundBytesAndStoreAsBytesAndClip(soundFile);
            Clip clip = storedClips.get(soundFile);
            play(id, clip, replay);
        }

        private void createNewClipFromStoredBytesAndPlay(int id, String sound, boolean replay) {
            Clip clip = retrieveNewOpenedClipFromSoundBytes(sound, storedSoundBytes.get(sound));
            addLineListener(id, clip);
            play(id, clip, replay);
        }

        private void play(int id, Clip clip, boolean replay) {
            clips.put(id, clip);
            if (replay) {
                muteOrUnmuteClip(clip, false);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                muteOrUnmuteClip(clip, false);
                clip.start();
            }
        }

        private boolean clipAlreadyAvailable(int id) {
            return clips.get(id) != null;
        }

        private void muteAndStopSound(int id) {
            Clip clip = clips.get(id);
            if (clip != null) {
                muteOrUnmuteClip(clip, true);
                clip.stop();
            }
        }

        private void muteOrUnmuteClip(Clip clip, boolean mute) {
            BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            if (muteControl != null) {
                muteControl.setValue(mute); // True to mute the line
            }
        }

        private void stopSound(int id) {
            if (clipAlreadyAvailable(id)) {
                muteAndStopSound(id);
            } else {
                new Thread(() -> {
                    while (clips.containsKey(id) && clips.get(id) == null) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ignored) {
                        }
                    }
                    muteAndStopSound(id);
                }).start();
            }
        }

        private void stopAllSounds() {
            for (Integer id : clips.keySet()) {
                stopSound(id);
            }
        }
    }

    private static class SwingAdapter {
        private final PaintingPanel paintingPanel;
        private final Frame frame;
        private Sound sound;
        private Mouse mouse;
        private Font activeFont;
        private HashMap<Character, Color> colorMap;
        private final ConcurrentHashMap<String, Font> storedFonts;
        private final HashMap<Integer, BufferedImage> imageMap;
        private double sizeOfImageMapInMB;
        private static final int IMAGE_MAP_LIMIT_IN_MB = 1000;
        private int imageMapRefreshCounter;
        private volatile boolean blockUntilFontIsLoaded;
        private Thread paintingThread;

        private SwingAdapter(Statistic statistic) {
            paintingPanel = new PaintingPanel(statistic);
            frame = new Frame(paintingPanel);
            activeFont = new Font("Monospaced", Font.PLAIN, 15);
            initColorMap();
            imageMap = new HashMap<>();
            storedFonts = new ConcurrentHashMap<>();
            storedFonts.put("standardfont", activeFont);
        }

        private void initialize() {
            showWindowAndCreateBufferStrategy();
            paintingThread = new Thread(() -> {
                while (!paintingThread.isInterrupted()) {
                    paintingPanel.paintImage();
                }
            });
            paintingThread.setDaemon(true);
            paintingThread.start();
        }

        private void showWindowAndCreateBufferStrategy() {
            frame.setVisible(true);
            paintingPanel.createBufferStrategy(2);
            paintingPanel.canvasBufferStrategy = paintingPanel.getBufferStrategy();
            Graphics2D graphics2D = (Graphics2D) paintingPanel.canvasBufferStrategy.getDrawGraphics();
            AffineTransform unScaledTransform = graphics2D.getTransform();
            paintingPanel.windowsScaleFactor = unScaledTransform.getScaleX();
            paintingPanel.updateScaleFactor();
        }

        private void setColorForBlockImage(char character, Color color) {
            colorMap.put(character, color);
        }

        private void registerListeners(Mouse mouse, Keyboard keyboard, Sound sound) {
            frame.registerListeners(mouse, keyboard);
            this.sound = sound;
            this.mouse = mouse;
        }

        private void initColorMap() {
            colorMap = new HashMap<>();
            colorMap.put('R', Color.RED);
            colorMap.put('r', Color.RED.darker());
            colorMap.put('G', Color.GREEN);
            colorMap.put('g', Color.GREEN.darker());
            colorMap.put('B', Color.BLUE);
            colorMap.put('b', Color.BLUE.darker());
            colorMap.put('Y', Color.YELLOW);
            colorMap.put('y', Color.YELLOW.darker());
            colorMap.put('P', Color.PINK);
            colorMap.put('p', Color.PINK.darker());
            colorMap.put('C', Color.CYAN);
            colorMap.put('c', Color.CYAN.darker());
            colorMap.put('M', Color.MAGENTA);
            colorMap.put('m', Color.MAGENTA.darker());
            colorMap.put('O', Color.ORANGE);
            colorMap.put('o', Color.ORANGE.darker());
            colorMap.put('W', Color.WHITE);
            colorMap.put('L', Color.BLACK);
        }

        // Anzeige
        private void setStatusText(String statusText) {
            SwingUtilities.invokeLater(() -> {
                frame.getStatusLabelLinks().setText(statusText);
                int minWidth = frame.getStatusBar().getPreferredSize().width + 50;
                frame.setMinimumSize(new Dimension(minWidth, minWidth / WIDTH * HEIGHT));
            });
        }

        private BufferedImage createImage(int width, int height, double scale) {
            return new BufferedImage((int) Math.ceil(width * scale), (int) Math.ceil(height * scale), BufferedImage.TYPE_INT_ARGB_PRE);
        }

        private Graphics2D createGraphics2D(BufferedImage image, double scale) {
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.scale(scale, scale);
            return graphics2D;
        }

        private BufferedImage createImageFromFile(String imageFileName, double imageScaleFactor) {
            int hash = Objects.hash(imageFileName, (int) Math.round(imageScaleFactor * 100), paintingPanel.scaleFactorHash);
            BufferedImage image = imageMap.get(hash);
            if (image == null) {
                if (imageScaleFactor <= 0) {
                    throw new IllegalArgumentException("scaleFactor has to be a positive number.");
                }
                Tools.checkLowerCaseFileNameCall(imageFileName);
                BufferedImage imageFromDisk = null;
                try {
                    imageFromDisk = ImageIO.read(Objects.requireNonNull(GameView.class.getResource(Tools.RESOURCE_PREFIX + imageFileName)));
                } catch (Exception e) {
                    throw new IllegalArgumentException("ImageFile \"" + imageFileName + "\" konnte nicht gefunden werden!");
                }
                double scale = paintingPanel.windowsScaleFactor * paintingPanel.panelScaleFactor * imageScaleFactor;
                int width = imageFromDisk.getWidth();
                int height = imageFromDisk.getHeight();
                image = createImage(width, height, scale);
                scale = image.getWidth() / (1d * width);
                Graphics2D graphics2D = createGraphics2D(image, scale);
                graphics2D.drawImage(imageFromDisk, 0, 0, null);
                graphics2D.dispose();
                addImageToMapOrClearMap(hash, image);
            }
            return image;
        }

        private BufferedImage createImageFromColorString(String colorString, double blockSize) {
            int roundedBlockSize = (int) Math.round(blockSize);
            int hash = Objects.hash(colorString, roundedBlockSize, paintingPanel.scaleFactorHash);
            BufferedImage image = imageMap.get(hash);
            if (image == null) {
                if (blockSize < 0.5) {
                    throw new IllegalArgumentException("blockSize has to be at least 0.5.");
                }
                String[] lines = colorString.split("\\R");
                double scale = paintingPanel.windowsScaleFactor * paintingPanel.panelScaleFactor;
                int width = Arrays.stream(lines).mapToInt(String::length).max().orElse(1) * roundedBlockSize;
                int height = lines.length * roundedBlockSize;
                image = createImage(width, height, scale);
                scale = image.getWidth() / (1d * width);
                double offsetToPreventRoundingErrors = 0.000000001;
                Graphics2D graphics2D = createGraphics2D(image, scale + offsetToPreventRoundingErrors);
                for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
                    char[] blocks = lines[lineIndex].toCharArray();
                    for (int columnIndex = 0; columnIndex < blocks.length; columnIndex++) {
                        Color color = colorMap.get(blocks[columnIndex]);
                        if (color != null) {
                            graphics2D.setColor(color);
                            graphics2D.fillRect(columnIndex * roundedBlockSize, lineIndex * roundedBlockSize, roundedBlockSize, roundedBlockSize);
                        }
                    }
                }
                graphics2D.dispose();
                addImageToMapOrClearMap(hash, image);
            }
            return image;
        }

        private BufferedImage createImageFromText(String text, double fontSize, Color color, boolean bold, String fontName) {
            int roundedFontSize = (int) Math.round(fontSize) + 1;
            int hash = Objects.hash(text, roundedFontSize, color, bold, fontName, paintingPanel.scaleFactorHash);
            BufferedImage image = imageMap.get(hash);
            if (image == null) {
                if (fontSize < 5) {
                    throw new IllegalArgumentException("fontSize has to be at least 5.");
                }
                if (text == null || text.isEmpty()) {
                    throw new IllegalArgumentException("Text can't be null or empty.");
                }
                Tools.checkLowerCaseFileNameCall(fontName);
                String[] lines = text.split("\\R");
                Font font = storedFonts.get(fontName);
                if (font != null) {
                    activeFont = font;
                } else {
                    if (!blockUntilFontIsLoaded) {
                        new Thread(() -> loadFont(fontName)).start();
                    }
                    return new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB_PRE);
                }
                Font imageFont = this.activeFont.deriveFont((float) roundedFontSize);
                if (bold) {
                    imageFont = imageFont.deriveFont(Font.BOLD);
                }
                FontMetrics fontMetrics = paintingPanel.getFontMetrics(imageFont);
                double scale = paintingPanel.windowsScaleFactor * paintingPanel.panelScaleFactor;
                int width = (int) Math.round(Arrays.stream(lines).mapToInt(fontMetrics::stringWidth).max().orElse(1) * 1.1);
                int height = fontMetrics.getHeight() * lines.length;
                image = createImage(width, height, scale);
                scale = image.getWidth() / (1d * width);
                Graphics2D graphics2D = createGraphics2D(image, scale);
                graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                graphics2D.setFont(imageFont);
                graphics2D.setColor(color);
                for (int i = 0; i < lines.length; i++) {
                    graphics2D.drawString(lines[i], 0, roundedFontSize + fontMetrics.getHeight() * i);
                }
                graphics2D.dispose();
                addImageToMapOrClearMap(hash, image);
            }
            return image;
        }

        private void addImageToMapOrClearMap(int hash, BufferedImage image) {
            if (sizeOfImageMapInMB > IMAGE_MAP_LIMIT_IN_MB || paintingPanel.scaleFactorChanged) {
                imageMap.clear();
                sizeOfImageMapInMB = 0;
                if (!paintingPanel.scaleFactorChanged) {
                    imageMapRefreshCounter++;
                }
                paintingPanel.scaleFactorChanged = false;
            }
            imageMap.put(hash, image);
            sizeOfImageMapInMB += image.getHeight() * image.getWidth() * 0.000004;
        }

        // Fenster-Dekorationen
        private void setTitle(String title) {
            frame.setTitle(title);
        }

        private void setWindowIcon(String iconFileName) {
            Image fensterSymbol = null;
            try {
                fensterSymbol = new ImageIcon(Objects.requireNonNull(GameView.class.getResource(Tools.RESOURCE_PREFIX + iconFileName))).getImage();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Symbolfile \"" + iconFileName + "\" konnte nicht gefunden werden!");
            }
            frame.setIconImage(fensterSymbol);
        }

        private void loadFont(String fontName) {
            blockUntilFontIsLoaded = true;
            Font font;
            InputStream is = GameView.class.getResourceAsStream(Tools.RESOURCE_PREFIX + fontName);
            if (is == null) {
                throw new IllegalArgumentException("File does not exist or wrong filename! Only .ttf-Fonts are supported: " + fontName);
            }
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (FontFormatException | IOException e) {
                throw new IllegalArgumentException("Font could not be loaded: " + fontName, e);
            }
            font = font.deriveFont((float) 15);
            activeFont = font;
            storedFonts.put(fontName, font);
            blockUntilFontIsLoaded = false;
        }

        // Maus Cursor
        private void setMouseCursor(String cursorImageFileName, boolean centered) {
            try {
                Image im = new ImageIcon(Objects.requireNonNull(GameView.class.getResource(Tools.RESOURCE_PREFIX + cursorImageFileName))).getImage();
                SwingUtilities.invokeLater(() -> paintingPanel.setCursor(createCursor(im, centered)));
            } catch (Exception e) {
                System.err.println("Cursor-Datei konnte nicht gefunden werden!");
                System.exit(1);
            }
        }

        private Cursor createCursor(Image im, boolean centered) {
            Toolkit toolkit = paintingPanel.getToolkit();
            Dimension cursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(64, 64);
            Point cursorHotSpot = new Point(0, 0);
            if (centered) {
                cursorHotSpot = new Point(cursorSize.width / 2, cursorSize.height / 2);
            }
            return toolkit.createCustomCursor(im, cursorHotSpot, "Cross");
        }

        private void setStandardMouseCursor() {
            SwingUtilities.invokeLater(() -> paintingPanel.setCursor(Cursor.getDefaultCursor()));
        }

        private void setInvisibleMouseCursor() {
            Image im = new ImageIcon("").getImage();
            SwingUtilities.invokeLater(() -> paintingPanel.setCursor(createCursor(im, false)));
        }

        // Beenden
        private void closeWindow(boolean kill) {
            if (kill) {
                System.exit(0);
            } else {
                if (paintingThread != null) {
                    paintingThread.interrupt();
                }
                sound.stopAllSounds();
                mouse.invisibleMouseTimer.stop();
                frame.dispose();
            }
        }

        private Dimension getTextDisplaySize() {
            return paintingPanel.getSize();
        }
    }

    private static class PaintingPanel extends java.awt.Canvas {
        private BufferStrategy canvasBufferStrategy;
        private double windowsScaleFactor;
        private double panelScaleFactor;
        private int scaleFactorHash;
        private java.awt.Rectangle bounds;
        private java.awt.Rectangle scaledBounds;
        private boolean scaleFactorChanged;
        private final AffineTransform identity;
        private AffineTransform scaledTransform;
        private final Statistic statistic;
        private long lastSleepCheckPoint;
        private ArrayList<PrintObject> printObjects;
        private Color backgroundColor;
        private volatile boolean framePainted;
        private int millisecondsPerFrame;

        private PaintingPanel(Statistic statistic) {
            this.statistic = statistic;
            setIgnoreRepaint(true);
            setSize(GameView.WIDTH, GameView.HEIGHT);
            identity = new AffineTransform();
            framePainted = true;
        }

        private void updateScaleFactor() {
            panelScaleFactor = Math.min(getWidth() * 1d / GameView.WIDTH, getHeight() * 1d / GameView.HEIGHT);
            scaleFactorHash = (int) (Math.round(panelScaleFactor * 1000));
            bounds = new java.awt.Rectangle(0, 0, GameView.WIDTH, GameView.HEIGHT);
            scaledBounds = new java.awt.Rectangle(0, 0, (int) Math.ceil(GameView.WIDTH * windowsScaleFactor * panelScaleFactor), (int) Math.ceil(GameView.HEIGHT * windowsScaleFactor * panelScaleFactor));
            scaledTransform = new AffineTransform();
            scaledTransform.scale(panelScaleFactor * windowsScaleFactor, panelScaleFactor * windowsScaleFactor);
            scaleFactorChanged = true;
        }

        private void setImageObjects(ArrayList<PrintObject> printObjects, Color backgroundColor) {
            this.printObjects = printObjects;
            this.backgroundColor = backgroundColor;
            framePainted = false;
        }

        private void paintImage() {
            if (!framePainted) {
                statistic.framesCounter++;
                paintImageToWindow(printObjects, backgroundColor);
                framePainted = true;
                sleepUntilEndOfFrame();
            }
        }

        private void sleepUntilEndOfFrame() {
            long timePassed = System.currentTimeMillis() - lastSleepCheckPoint;
            long sleepTime = millisecondsPerFrame - timePassed;
            if (sleepTime > 0) {
                Tools.sleep(sleepTime);
            }
            lastSleepCheckPoint = System.currentTimeMillis();
        }

        private void paintImageToWindow(ArrayList<PrintObject> printObjects, Color backgroundColor) {
            do {
                do {
                    Graphics2D graphics2D = (Graphics2D) canvasBufferStrategy.getDrawGraphics();
                    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    graphics2D.setTransform(scaledTransform);
                    statistic.drawImageTic();
                    draw(graphics2D, printObjects, backgroundColor);
                    statistic.drawImageToc();
                    graphics2D.dispose();
                } while (canvasBufferStrategy.contentsRestored());
                statistic.paintImageTic();
                canvasBufferStrategy.show();
                statistic.paintImageToc();
            } while (canvasBufferStrategy.contentsLost());
        }

        private void draw(Graphics2D graphics2D, ArrayList<PrintObject> printObjects, Color backgroundColor) {
            graphics2D.setColor(backgroundColor);
            graphics2D.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            for (PrintObject p : printObjects) {
                graphics2D.setColor(p.color);
                switch (p.type) {
                    case OVAL -> {
                        Oval oval = (Oval) p;
                        int x = oval.x - oval.width / 2;
                        int y = oval.y - oval.height / 2;
                        if (oval.filled) {
                            graphics2D.fillOval(x, y, oval.width + oval.lineWeight, oval.height + oval.lineWeight);
                        } else {
                            graphics2D.setStroke(new BasicStroke(oval.lineWeight));
                            graphics2D.drawOval(x + oval.lineWeight / 2, y + oval.lineWeight / 2, oval.width, oval.height);
                        }
                    }
                    case LINE -> {
                        Line line = (Line) p;
                        graphics2D.setStroke(new BasicStroke(line.lineWeight));
                        graphics2D.drawLine(line.x, line.y, line.xEnd, line.yEnd);
                    }
                    case RECTANGLE -> {
                        Rectangle rectangle = (Rectangle) p;
                        if (rectangle.filled) {
                            graphics2D.fillRect(rectangle.x, rectangle.y, rectangle.width + rectangle.lineWeight, rectangle.height + rectangle.lineWeight);
                        } else {
                            graphics2D.setStroke(new BasicStroke(rectangle.lineWeight));
                            graphics2D.drawRect(rectangle.x + rectangle.lineWeight / 2, rectangle.y + rectangle.lineWeight / 2, rectangle.width, rectangle.height);
                        }
                    }
                    case POLYGON -> {
                        Polygon polygon = (Polygon) p;
                        if (polygon.filled) {
                            graphics2D.fillPolygon(polygon.xCoordinates, polygon.yCoordinates, polygon.xCoordinates.length);
                        } else {
                            graphics2D.setStroke(new BasicStroke(polygon.lineWeight));
                            graphics2D.drawPolygon(polygon.xCoordinates, polygon.yCoordinates, polygon.xCoordinates.length);
                        }
                    }
                    case POLYLINE -> {
                        PolyLine polyLine = (PolyLine) p;
                        graphics2D.setStroke(new BasicStroke(polyLine.lineWeight));
                        graphics2D.drawPolyline(polyLine.xCoordinates, polyLine.yCoordinates, polyLine.xCoordinates.length);
                    }
                    case IMAGE_OBJECT -> {
                        ImageObject imageObject = (ImageObject) p;
                        graphics2D.setTransform(identity);
                        if (imageObject.rotation != 0) {
                            AffineTransform rotationTransform = graphics2D.getTransform();
                            graphics2D.translate(imageObject.x, imageObject.y);
                            rotationTransform.rotate(Math.toRadians(imageObject.rotation), imageObject.image.getWidth() / 2.0, imageObject.image.getHeight() / 2.0);
                            graphics2D.drawImage(imageObject.image, rotationTransform, null);
                        } else {
                            graphics2D.drawImage(imageObject.image, imageObject.x, imageObject.y, null);
                        }
                        graphics2D.setTransform(scaledTransform);
                    }
                }
            }
        }
    }

    private class Statistic {
        private long gameLogicStartTime;
        private long gameLogicAverageDuration;
        private long drawImageStartTime;
        private long drawImageAverageDuration;
        private long paintImageStartTime;
        private long paintImageAverageDuration;
        private long lastStatisticsUpdateTime;
        private boolean showStatistics;
        private int cyclesCounter;
        private int framesCounter;
        private int invisiblePrintObjects;
        private int boxYPosition;

        private int loopsPerSecondValue;
        private int framesPerSecondValue;
        private int gameViewValue;
        private int graphicValue;
        private int gameValue;
        private int visibleValue;
        private int invisibleValue;
        private int bufferSizeValue;
        private int bufferOverflowValue;
        private boolean started;

        private void updateStatistic() {
            long currentTime = System.currentTimeMillis();
            long timePassed = currentTime - lastStatisticsUpdateTime;
            if (timePassed > 1_000) {
                if (!started) {
                    started = true;
                    loopsPerSecondValue = 60;
                    framesPerSecondValue = 60;
                    cyclesCounter = 0;
                    framesCounter = 0;
                    gameViewValue = 1;
                    graphicValue = 1;
                    gameValue = 1;
                    lastStatisticsUpdateTime = currentTime;
                    return;
                }
                // FPS
                loopsPerSecondValue = cyclesCounter;
                framesPerSecondValue = framesCounter;
                cyclesCounter = 0;
                framesCounter = 0;

                // Average Times
                gameViewValue = (int) Math.max(1, drawImageAverageDuration);
                graphicValue = (int) Math.max(1, paintImageAverageDuration);
                gameValue = (int) Math.max(1, gameLogicAverageDuration);

                // PrintObjects
                int numberOfStatisticObjects = 35; // SimpleStartScreen has 8 PrintObjects
                visibleValue = Math.max(0, swingAdapter.paintingPanel.printObjects.size() - numberOfStatisticObjects);
                invisibleValue = invisiblePrintObjects;

                // Image buffer
                bufferSizeValue = (int) swingAdapter.sizeOfImageMapInMB;
                bufferOverflowValue = swingAdapter.imageMapRefreshCounter;
                lastStatisticsUpdateTime = currentTime;
            }
            if (showStatistics) {
                boxYPosition = 5;
                addBox(new Title("Bildraten"), new Line("Loops/Sekunde:", loopsPerSecondValue, null, false, 54, 50), new Line("Bilder/Sekunde:", framesPerSecondValue, null, false, 50, 28));
                addBox(new Title("16 ms pro Bild"), new Line("GameView:", gameViewValue, "ms", true, 10, 20), new Line("Grafikkarte:", graphicValue, "ms", true, 15, 20), new Line("Spiel-Logik:", gameValue, "ms", true, 2, 3));
                addBox(new Title("Spiel-Objekte"), new Line("Sichtbar:", visibleValue, null, true, 200, 300), new Line("Unsichtbar:", invisibleValue, null, true, 100, 200));
                addBox(new Title("Bildpuffer"), new Line("Größe:", bufferSizeValue, "MB", true, 500, 700), new Line("Überläufe:", bufferOverflowValue, null, true, 1, 2));
            }
        }

        private void addBox(Title title, Line... lines) {
            int boxXPosition = 5;
            int gap = 5;
            int width = 175;
            int height = 3 * gap + Title.height + lines.length * Line.height;
            addRectangleToCanvas(boxXPosition + 1, boxYPosition + 1, width, height, 0, true, Color.BLACK);
            addRectangleToCanvas(boxXPosition, boxYPosition, width, height, 2, false, Color.WHITE);
            boxYPosition += gap;
            title.add(boxYPosition);
            boxYPosition += Title.height + gap;
            for (Line line : lines) {
                line.add(boxYPosition);
                boxYPosition += Line.height;
            }
            boxYPosition += gap;
        }

        private class Element {
            protected static final int fontSize = 12;
            protected static final int xPosition1 = 15;
            protected String name;
            protected Color color;
            protected static int height;

            public Element(String name, Color color) {
                this.name = name;
                this.color = color;
            }
        }

        private class Title extends Element {

            public Title(String description) {
                super(description, Color.WHITE);
                height = (int) (fontSize * 1.2);
            }

            private void add(int yPosition) {
                addTextToCanvas(centeredTitle(), xPosition1, yPosition, fontSize, true, color, 0);
            }

            private String centeredTitle() {
                int gap = (21 - name.length()) / 2;
                return " ".repeat(gap) + name;
            }
        }

        private class Line extends Element {
            private static final int xPosition2 = 130;
            private static final int xPosition3 = 160;
            private final int value;
            private final String measure;

            public Line(String description, int value, String measure, boolean lowIsGood, int dangerous, int critical) {
                super(description, lowIsGood ? chooseColorLowIsGood(value, dangerous, critical) : chooseColorHighIsGood(value, dangerous, critical));
                this.value = value;
                this.measure = measure;
                height = (int) (fontSize * 1.2);
            }

            private void add(int yPosition) {
                addTextToCanvas(name, xPosition1, yPosition, fontSize, false, color, 0);
                addTextToCanvas(numberWithHundredGap(value), xPosition2, yPosition, fontSize, false, color, 0);
                if (measure != null) {
                    addTextToCanvas(measure, xPosition3, yPosition, fontSize, false, color, 0);
                }
            }

            private static Color chooseColorHighIsGood(int value, int dangerous, int critical) {
                if (value <= critical) {
                    return Color.RED;
                } else if (value <= dangerous) {
                    return Color.ORANGE;
                } else {
                    return Color.WHITE;
                }
            }

            private static Color chooseColorLowIsGood(int value, int dangerous, int critical) {
                if (value >= critical) {
                    return Color.RED;
                } else if (value >= dangerous) {
                    return Color.ORANGE;
                } else {
                    return Color.WHITE;
                }
            }

            private String numberWithHundredGap(int number) {
                String gap = "";
                if (number < 10) {
                    gap += " ";
                }
                if (number < 100) {
                    gap += " ";
                }
                return gap + number;
            }
        }

        private void gameLogicTic() {
            gameLogicStartTime = System.currentTimeMillis();
        }

        private void drawImageTic() {
            drawImageStartTime = System.currentTimeMillis();
        }

        private void paintImageTic() {
            paintImageStartTime = System.currentTimeMillis();
        }

        private void gameLogicToc() {
            gameLogicAverageDuration = toc(gameLogicAverageDuration, gameLogicStartTime);
        }

        private void drawImageToc() {
            drawImageAverageDuration = toc(drawImageAverageDuration, drawImageStartTime);
        }

        private void paintImageToc() {
            paintImageAverageDuration = toc(paintImageAverageDuration, paintImageStartTime);
        }

        private long toc(long currentAverage, long startTime) {
            return Math.min(100, (4 * currentAverage + System.currentTimeMillis() - startTime) / 5);
        }
    }

    private class GameLoop {
        private long lastSleepCheckPoint;
        private static final int FRAMES_PER_SECOND = 60;
        private final int millisecondsPerFrame;

        private GameLoop() {
            millisecondsPerFrame = 1000 / FRAMES_PER_SECOND;
            swingAdapter.paintingPanel.millisecondsPerFrame = millisecondsPerFrame;
        }

        private void gameLoopWithStatistics() {
            statistic.cyclesCounter++;
            gameLoopManager();
            if (gameViewClosed) {
                return;
            }
            if (swingAdapter.paintingPanel.framePainted) {
                statistic.updateStatistic();
                swingAdapter.paintingPanel.setImageObjects(canvas.printObjects, canvas.backgroundColor);
                canvas.printObjects = new ArrayList<>();
            } else {
                canvas.printObjects.clear();
            }
            statistic.invisiblePrintObjects = 0;
            sleepUntilEndOfFrame();
        }

        private void gameLoopManager() {
            statistic.gameLogicTic();
            if (!showScreen) {
                gameLoop();
            }
            statistic.gameLogicToc();
        }

        private void sleepUntilEndOfFrame() {
            long timePassed = System.currentTimeMillis() - lastSleepCheckPoint;
            long sleepTime = millisecondsPerFrame - timePassed;
            if (sleepTime > 0) {
                Tools.sleep(sleepTime);
            }
            lastSleepCheckPoint = System.currentTimeMillis();
        }
    }

    private static class StartScreenWithChooseBox {
        private final GameView gameView;

        private final int lineWeight;
        private final int titleFontSize;
        private final String title;
        private final int titleHeight;
        private final Color font;
        private final Color frameAndTitle;

        private final String description;
        private final int descriptionFontSize;
        private final int yDescription;

        private final SelectionBox selectionBox;
        private final int selectionBoxLineWeight;
        private final int xSelectionBox;

        private final int enterBoxWidth;
        private final int enterBoxHeight;
        private final int ySelectionBox;
        private final java.awt.Rectangle enterBox;

        private final int yLowerLine;

        private boolean startScreenClosed;
        private final boolean useMouseBackup;


        private StartScreenWithChooseBox(GameView gameView, String title, String description, String selectionTitle, String[] selectionItems, int selectedItem) {
            this.gameView = gameView;
            this.lineWeight = 5;
            this.title = title;
            this.titleFontSize = 45;
            this.titleHeight = (int) Math.rint(titleFontSize * 1.5);
            this.font = Color.GRAY;
            this.frameAndTitle = Color.WHITE;

            this.description = description;
            this.descriptionFontSize = 16;
            this.yDescription = titleHeight + 2 * lineWeight;

            int gap = 20;
            int selectionFontSize = 20;
            this.selectionBoxLineWeight = (int) Math.rint(selectionFontSize / 8d);
            this.selectionBox = new SelectionBox(gameView, selectionTitle, selectionItems, selectedItem, selectionFontSize, selectionBoxLineWeight, font, Color.YELLOW, Color.BLACK, frameAndTitle);
            this.xSelectionBox = gap;
            this.ySelectionBox = HEIGHT - selectionBox.getHeight() - gap;

            this.enterBoxWidth = WIDTH / 5 - 2 * gap;
            this.enterBoxHeight = 4 * descriptionFontSize;
            int yEnterBox = HEIGHT - enterBoxHeight - gap;
            this.enterBox = new java.awt.Rectangle(WIDTH - enterBoxWidth - gap, yEnterBox, enterBoxWidth, enterBoxHeight);

            this.yLowerLine = Math.min(ySelectionBox - gap, yEnterBox - gap);
            this.startScreenClosed = false;
            useMouseBackup = gameView.mouseEnabled();
            gameView.useMouse(true);
        }

        private void printStartScreen() {
            while (!startScreenClosed) {
                checkUserInput();
                addRectangles();
                addTitle();
                addDescription();
                selectionBox.addSelectionBox(xSelectionBox, ySelectionBox);
                addEnterField();
                gameView.gameLoop.gameLoopWithStatistics();
            }
            gameView.useMouse(useMouseBackup);
        }

        private void checkUserInput() {
            // Tastendruck
            KeyEvent[] keyEvents = gameView.keyEvents();
            for (KeyEvent keyEvent : keyEvents) {
                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                        selectionBox.up();
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                        selectionBox.down();
                    } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                        startScreenClosed = true;
                    }
                }
            }
            // Mausklick
            MouseEvent[] mouseEvents = gameView.mouseEvents();
            for (MouseEvent mouseEvent : mouseEvents) {
                if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
                    selectionBox.processMouseClick(mouseEvent.getX(), mouseEvent.getY());
                    if (enterBox.contains(mouseEvent.getX(), mouseEvent.getY())) {
                        startScreenClosed = true;
                    }
                }
            }
        }

        private void addRectangles() {
            gameView.addRectangleToCanvas(lineWeight / 2d, lineWeight / 2d, WIDTH - 1 - lineWeight, HEIGHT - 1 - lineWeight, lineWeight, false, font);
            gameView.addRectangleToCanvas(lineWeight / 2d, lineWeight / 2d, WIDTH - 1 - lineWeight, titleHeight - lineWeight, lineWeight, false, font);
            gameView.addRectangleToCanvas(lineWeight / 2d, yLowerLine + lineWeight / 2d, WIDTH - 1 - lineWeight, HEIGHT - yLowerLine - lineWeight, lineWeight, false, font);
        }

        private void addTitle() {
            gameView.addTextToCanvas(title, (WIDTH - title.length() * titleFontSize) / 2d, ((int) (titleFontSize * 1.5) - titleFontSize) / 2d, titleFontSize, true, frameAndTitle, 0);
        }

        private void addDescription() {
            gameView.addTextToCanvas(description, 2 * lineWeight, yDescription, descriptionFontSize, true, font, 0);
        }

        private void addEnterField() {
            gameView.addRectangleToCanvas(enterBox.x, enterBox.y, enterBox.width, enterBox.height, selectionBoxLineWeight, false, frameAndTitle);
            int gap = 2 * selectionBoxLineWeight;
            gameView.addRectangleToCanvas(enterBox.x + gap, enterBox.y + gap, enterBox.width - 2 * gap, enterBox.height - 2 * gap, selectionBoxLineWeight, false, frameAndTitle);
            String text = "Press ENTER or\n" + "click to start";
            int tmpTitleWidth = 14 * descriptionFontSize;
            int tmpTitleHeight = 2 * descriptionFontSize;
            gameView.addTextToCanvas(text, enterBox.x + (enterBoxWidth - tmpTitleWidth * 0.65) / 2d, enterBox.y + (enterBoxHeight - tmpTitleHeight * 3 / 2d) / 2d, descriptionFontSize, true, frameAndTitle, 0);
        }

        private int getSelectedItem() {
            return selectionBox.getSelectedItem();
        }

        private static class SelectionBox {
            private final GameView gameView;
            private final String title;
            private final String[] items;
            private final int fontSize;
            private int selectedItem;
            private final Color markerFont;
            private final Color markerHighlight;
            private final Color markerRectangle;
            private final Color frameAndTitle;

            private final int lineWeight;
            private final int titleHeight;
            private final int heightOfMarkerField;
            private final int heightOfMarkerBox;
            private final int height;
            private int widthOfMarkerField;
            private final int width;

            private final java.awt.Rectangle[] markerBounds;
            private final java.awt.Rectangle upBounds;
            private final java.awt.Rectangle downBounds;

            private int x;
            private int xLine;
            private int y;
            private int yMarkerBox;

            private SelectionBox(GameView gameView, String title, String[] items, int selectedItem, int fontSize, int lineWeight, Color markerFont, Color markerHighlight, Color markerRectangle, Color frameAndTitle) {
                this.gameView = gameView;
                this.title = title;
                this.items = items;
                this.fontSize = fontSize;
                this.selectedItem = selectedItem;
                this.markerFont = markerFont;
                this.markerHighlight = markerHighlight;
                this.markerRectangle = markerRectangle;
                this.frameAndTitle = frameAndTitle;

                this.lineWeight = lineWeight;
                this.titleHeight = (int) Math.rint(fontSize * 1.6);
                this.heightOfMarkerField = (int) Math.rint(fontSize * 1.25);
                this.heightOfMarkerBox = items.length * heightOfMarkerField + 2 * lineWeight;
                this.height = titleHeight + heightOfMarkerBox - lineWeight;
                calculateWidthOfMarkerField();
                this.width = widthOfMarkerField + 6 * lineWeight;

                this.markerBounds = new java.awt.Rectangle[items.length];
                for (int i = 0; i < items.length; i++) {
                    markerBounds[i] = new java.awt.Rectangle(0, 0, widthOfMarkerField, heightOfMarkerField);
                }
                this.upBounds = new java.awt.Rectangle(0, 0, 5 * lineWeight, markerBounds[0].height);
                this.downBounds = new java.awt.Rectangle(0, 0, 5 * lineWeight, markerBounds[items.length - 1].height);
            }

            private void calculateWidthOfMarkerField() {
                int letters = title.strip().length();
                for (String name : items) {
                    if (name.strip().length() > letters) {
                        letters = name.strip().length();
                    }
                }
                this.widthOfMarkerField = letters * fontSize + 2 * lineWeight;
            }

            private void addSelectionBox(int x, int y) {
                this.x = x + lineWeight / 2;
                this.xLine = x + lineWeight;
                this.y = y + lineWeight / 2;
                this.yMarkerBox = y + titleHeight - lineWeight + lineWeight / 2;
                addTitleBox();
                addMarkerFields();
                addNavigationBox();
            }

            private void addTitleBox() {
                gameView.addRectangleToCanvas(x, y, width - lineWeight, titleHeight - lineWeight, lineWeight, false, frameAndTitle);
                gameView.addTextToCanvas(title, xLine + (widthOfMarkerField - title.length() * fontSize) / 2d, y + (titleHeight - fontSize) / 2d, fontSize, true, frameAndTitle, 0);
            }

            private void addMarkerFields() {
                gameView.addRectangleToCanvas(x, yMarkerBox, widthOfMarkerField, heightOfMarkerBox - lineWeight, lineWeight, false, frameAndTitle);
                int yMarkerField = yMarkerBox + lineWeight / 2;
                for (int i = 0; i < items.length; i++) {
                    boolean isSelected = (i == selectedItem);
                    markerBounds[i].x = xLine;
                    markerBounds[i].y = yMarkerField + i * heightOfMarkerField;
                    addMarkerField(markerBounds[i], items[i], isSelected);
                }
                upBounds.x = markerBounds[0].x + markerBounds[0].width;
                upBounds.y = markerBounds[0].y;
                downBounds.x = markerBounds[items.length - 1].x + markerBounds[items.length - 1].width;
                downBounds.y = markerBounds[items.length - 1].y;
            }

            private void addMarkerField(java.awt.Rectangle bounds, String name, boolean isMarked) {
                if (isMarked) {
                    gameView.addRectangleToCanvas(bounds.x + lineWeight / 2d, bounds.y + lineWeight / 2d, bounds.width - lineWeight, bounds.height - lineWeight, lineWeight, true, markerHighlight);
                    gameView.addRectangleToCanvas(bounds.x + lineWeight / 2d, bounds.y + lineWeight / 2d, bounds.width - lineWeight, bounds.height - lineWeight, 1, false, markerRectangle);
                } else {
                    gameView.addRectangleToCanvas(bounds.x, bounds.y, bounds.width, bounds.height, 1, false, markerFont);
                }
                gameView.addTextToCanvas(name, bounds.x + lineWeight, bounds.y + (bounds.height - fontSize * 3 / 2d) / 2d, fontSize, true, markerFont, 0);
            }

            private void addNavigationBox() {
                int xUpDown = x + widthOfMarkerField + lineWeight;
                int yUp = yMarkerBox + lineWeight + fontSize / 2 + 2 * lineWeight;
                int yDown = yMarkerBox + items.length * heightOfMarkerField - fontSize / 2 - 2 * lineWeight;
                gameView.addRectangleToCanvas(xUpDown - lineWeight, yMarkerBox, 5 * lineWeight, items.length * heightOfMarkerField + lineWeight, lineWeight, false, frameAndTitle);
                gameView.addPolygonToCanvas(new double[]{xUpDown + lineWeight, xUpDown + 3 * lineWeight, xUpDown + 2 * lineWeight}, new double[]{yUp, yUp, yUp - fontSize / 2d}, 1, true, frameAndTitle);
                gameView.addPolygonToCanvas(new double[]{xUpDown + lineWeight, xUpDown + 3 * lineWeight, xUpDown + 2 * lineWeight}, new double[]{yDown, yDown, yDown + fontSize / 2d}, 1, true, frameAndTitle);
            }

            void processMouseClick(int x, int y) {
                for (int i = 0; i < markerBounds.length; i++) {
                    if (markerBounds[i].contains(x, y)) {
                        selectedItem = i;
                    }
                }
                if (upBounds.contains(x, y)) {
                    up();
                } else if (downBounds.contains(x, y)) {
                    down();
                }
            }

            void up() {
                if (selectedItem > 0) {
                    selectedItem--;
                }
            }

            void down() {
                if (selectedItem < items.length - 1) {
                    selectedItem++;
                }
            }

            int getHeight() {
                return height;
            }

            int getSelectedItem() {
                return selectedItem;
            }
        }
    }

    private static class Screen {
        protected final GameView gameView;
        protected final int gap;
        protected final int fontSize;
        protected final boolean useMouseBackup;
        protected boolean screenClosed;
        protected SelectionManager selectionManager;
        protected ArrayList<SimpleBox> simpleBoxes;

        protected Screen(GameView gameView, int gap, int fontSize) {
            this.gameView = gameView;
            this.gap = gap;
            this.fontSize = fontSize;
            this.useMouseBackup = gameView.mouseEnabled();
            this.gameView.useMouse(true);
        }

        protected void setSimpleBoxes(ArrayList<SimpleBox> simpleBoxes, int highLighted) {
            this.simpleBoxes = simpleBoxes;
            this.selectionManager = new SelectionManager(simpleBoxes, highLighted);
        }

        protected void checkUserInput() {
            for (KeyEvent keyEvent : gameView.keyEvents()) {
                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                    selectionManager.processKeyEvent(keyEvent);
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                        screenClosed = true;
                    }
                }
            }
            for (MouseEvent mouseEvent : gameView.mouseEvents()) {
                if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
                    if (selectionManager.processMouseEvent(mouseEvent.getX(), mouseEvent.getY())) {
                        screenClosed = true;
                    }
                }
            }
        }

        protected Dimension calculateBounds(String text) {
            String[] lines = text.split("\\R");
            int longestLine = Arrays.stream(lines).mapToInt(String::length).max().orElse(1);
            return new Dimension(longestLine, Math.max(1, lines.length));
        }

        protected int calculateFontSizeForBounds(Dimension textBounds, int height) {
            return (Math.min(WIDTH / textBounds.width, height / textBounds.height) - 1);
        }

        private static class SelectionManager {
            private final ArrayList<SimpleBox> simpleBoxes;
            private int highlightedBox;

            private SelectionManager(ArrayList<SimpleBox> simpleBoxes, int highlightedBox) {
                this.simpleBoxes = simpleBoxes;
                this.highlightedBox = highlightedBox;
                this.simpleBoxes.get(highlightedBox).isHighlighted = true;
            }

            private SimpleBox getSelectedItem() {
                return simpleBoxes.get(highlightedBox);
            }

            private void processKeyEvent(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                    highlight(highlightedBox + 1);
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                    highlight(highlightedBox - 1);
                }
            }

            private boolean processMouseEvent(int x, int y) {
                for (int i = 0; i < simpleBoxes.size(); i++) {
                    SimpleBox simpleBox = simpleBoxes.get(i);
                    if (simpleBox.contains(x, y)) {
                        highlight(i);
                        return true;
                    }
                }
                return false;
            }

            private void highlight(int boxToHighlight) {
                if (boxToHighlight >= 0 && boxToHighlight < simpleBoxes.size()) {
                    simpleBoxes.forEach(s -> s.isHighlighted = false);
                    simpleBoxes.get(boxToHighlight).isHighlighted = true;
                    highlightedBox = boxToHighlight;
                }
            }
        }
    }

    private static class EndScreen extends Screen {
        private final String message;

        private EndScreen(GameView gameView, String message) {
            super(gameView, 20, 28);
            this.message = message;
            int height = 40;
            int width = 250;
            int x = (WIDTH - 2 * width - gap) / 2;
            int y = HEIGHT - height - gap;
            ArrayList<SimpleBox> simpleBoxes = new ArrayList<>(3);
            simpleBoxes.add(new SimpleBox("New Game", x, y, width, height));
            simpleBoxes.add(new SimpleBox(Tools.CLOSE, x + width + gap, y, width, height));
            setSimpleBoxes(simpleBoxes, 0);
        }

        private void printEndScreen() {
            while (!screenClosed) {
                checkUserInput();
                addMessageToCanvas();
                simpleBoxes.forEach(s -> s.addToCanvas(gameView));
                gameView.gameLoop.gameLoopWithStatistics();
            }
            gameView.useMouse(useMouseBackup);
        }

        private void addMessageToCanvas() {
            Dimension messageBounds = calculateBounds(message);
            int x = (int) (GameView.WIDTH - messageBounds.width * fontSize * 0.65) / 2;
            int y = (GameView.HEIGHT - messageBounds.height * fontSize - 200) / 2;
            gameView.addTextToCanvas(message, x, y, fontSize, false, Color.WHITE, 0);
        }

        private boolean playAgain() {
            return simpleBoxes.get(0).isHighlighted;
        }
    }

    private static class SimpleStartScreen extends Screen {
        private final int titleHeight;
        private final String title;
        private final Color titleColor;
        private final String description;

        private SimpleStartScreen(GameView gameView, String title, String description, boolean easy) {
            super(gameView, 20, 16);
            this.title = title;
            this.titleHeight = HEIGHT / 4;
            this.titleColor = Color.RED.brighter();
            this.description = description;
            int height = 40;
            int width = 200;
            int x = (WIDTH - 3 * width - 2 * gap) / 2;
            int y = HEIGHT - height - gap;
            ArrayList<SimpleBox> simpleBoxes = new ArrayList<>(3);
            simpleBoxes.add(new SimpleBox("Easy", x, y, width, height));
            simpleBoxes.add(new SimpleBox("Standard", x + width + gap, y, width, height));
            simpleBoxes.add(new SimpleBox(Tools.CLOSE, x + 2 * width + 2 * gap, y, width, height));
            if (easy) {
                setSimpleBoxes(simpleBoxes, 0);
            } else {
                setSimpleBoxes(simpleBoxes, 1);
            }
        }

        private String getSelectedItem() {
            return selectionManager.getSelectedItem().text;
        }

        private void printStartScreen() {
            while (!screenClosed) {
                checkUserInput();
                addTitle();
                gameView.addTextToCanvas(description, gap, titleHeight + gap, fontSize, false, Color.WHITE, 0);
                simpleBoxes.forEach(s -> s.addToCanvas(gameView));
                gameView.gameLoop.gameLoopWithStatistics();
            }
            gameView.useMouse(useMouseBackup);
        }

        private void addTitle() {
            Dimension textBounds = calculateBounds(title);
            int fontSize = calculateFontSizeForBounds(textBounds, titleHeight);
            gameView.addTextToCanvas(title, (WIDTH - (textBounds.width * fontSize * 0.65)) / 2d, (titleHeight - (textBounds.height * fontSize)) / 2d, fontSize, true, titleColor, 0);
        }
    }

    private static class SimpleBox extends java.awt.Rectangle {
        private final String text;
        private boolean isHighlighted;
        private final int fontSize;

        private SimpleBox(String text, int x, int y, int width, int height) {
            super(x, y, width, height);
            this.text = text;
            this.fontSize = height * 2 / 3;
        }

        private void addToCanvas(GameView gameView) {
            if (isHighlighted) {
                gameView.addRectangleToCanvas(x, y, width, height, 3, true, Color.DARK_GRAY);
                gameView.addRectangleToCanvas(x, y, width, height, 3, false, Color.YELLOW);
            } else {
                gameView.addRectangleToCanvas(x, y, width, height, 3, false, Color.WHITE);
            }
            gameView.addTextToCanvas(text, x + (width - text.length() * fontSize * 0.65) / 2d, y + (height - fontSize * 3 / 2d) / 2d, fontSize, true, Color.WHITE, 0);
        }
    }

    private static class Tools {
        public static final String RESOURCE_PREFIX = "/resources/";
        public static final String GAMEVIEW_CLOSED = "GameView is closed. Call \"startGame()\" first.";
        public static final String CLOSE = "Close";
        public static final String NEGATIVE_LINEWEIGHT = "lineWeight can't be negative.";
        private List<Path> resourceFiles = new ArrayList<>();

        private Tools() {
            findResources();
        }

        private void checkResources() {

            try {
                enforceLowerCaseLettersForResourceFileNames();
                enforceSmallFileSizes();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        private void findResources() {
            try {
                URL url = GameView.class.getResource("/resources");
                if (url != null && url.getProtocol().equals("jar")) {
                    return;
                }
                Path pathToResources = new File(Objects.requireNonNull(url).toURI()).toPath();
                try (Stream<Path> stream = Files.walk(pathToResources, 10).filter(path -> !Files.isDirectory(path))) {
                    resourceFiles = stream.toList();
                }
            } catch (Exception ignored) {
            }
        }

        private void enforceLowerCaseLettersForResourceFileNames() {
            for (Path resourceFile : resourceFiles) {
                String fileName = resourceFile.getFileName().toString();
                if (!fileName.toLowerCase().equals(fileName)) {
                    throw new InputMismatchException("Only lower case letters are allowed " + "for resource files in \"src/resources\": " + fileName);
                }
            }
        }

        private void enforceSmallFileSizes() {
            long pngs = 0;
            long wavs = 0;
            long ttfs = 0;
            long all = 0;
            for (Path resourceFile : resourceFiles) {
                String fileName = resourceFile.getFileName().toString();
                long fileSize;
                try {
                    fileSize = Files.size(resourceFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                all += fileSize;
                if (fileName.endsWith(".png")) {
                    pngs += fileSize;
                    if (fileSize > 300_000) {
                        System.err.println("Bilder dürfen nicht mehr als 300 KB haben: " + fileName);
                    }
                } else if (fileName.endsWith(".wav")) {
                    wavs += fileSize;
                    if (fileSize > 2_000_000) {
                        System.err.println("Sound-Dateien dürfen nicht mehr als 2 MB haben: " + fileName);
                    }
                } else if (fileName.endsWith(".ttf")) {
                    ttfs += fileSize;
                    if (fileSize > 250_000) {
                        System.err.println("Font-Dateien dürfen nicht mehr als 250 KB haben: " + fileName);
                    }
                }
            }
            if (pngs > 5_000_000) {
                System.err.println("Alle Bild-Dateien dürfen zusammen nicht mehr als 5 MB haben: Ihre Bild-Dateien haben zusammen: " + pngs + " MB");
            }
            if (wavs > 8_000_000) {
                System.err.println("Alle Sound-Dateien dürfen zusammen nicht mehr als 8 MB haben: Ihre Sound-Dateien haben zusammen: " + wavs + " MB");
            }
            if (ttfs > 500_000) {
                System.err.println("Alle Font-dateien dürfen zusammen nicht mehr als 500 KB haben: Ihre Font-Dateien haben zusammen: " + ttfs + " KB");
            }

            if (all > 9_500_000) {
                System.err.println("Alle Dateien im Ordner \"resources\" dürfen zusammen nicht mehr als 9,5 MB haben: Ihre Dateien haben zusammen: " + all + " MB");
            }
        }

        private static void sleep(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException ignored) {
            }
        }

        private static void checkLowerCaseFileNameCall(String fileName) {
            if (!fileName.toLowerCase().equals(fileName)) {
                throw new IllegalArgumentException("Resource files in \"src/resources\" are written in lower case. " + "However a call contained upper case letters: " + fileName);
            }
        }
    }

    private static class Version {
        private static final int MAJOR = 2;
        private static final int MINOR = 3;
        private static final int UPDATE = 3;

        private static final String VERSION = MAJOR + "." + MINOR + "." + UPDATE;

        private static final String STANDARD_TITLE = "GameView";
        private static final String SIGNATURE = "Prof. Dr. Andreas Berl - TH Deggendorf";

        private static String getStatusSignature() {
            return "   " + SIGNATURE + " ";
        }

        private static String getStandardTitle() {
            return STANDARD_TITLE + " " + VERSION;
        }

        private static boolean isSmallerThan(String versionString) {
            String[] split = versionString.split("\\.");
            int major = Integer.parseInt(split[0]);
            int minor = Integer.parseInt(split[1]);
            int update = Integer.parseInt(split[2]);
            if (MAJOR != major) {
                return MAJOR < major;
            } else if (MINOR != minor) {
                return MINOR < minor;
            } else if (UPDATE != update) {
                return UPDATE < update;
            } else {
                return false;
            }
        }
    }
}