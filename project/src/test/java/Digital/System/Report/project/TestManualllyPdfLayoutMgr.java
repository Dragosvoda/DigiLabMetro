// Copyright 2013-03-13 PlanBase Inc. & Glen Peterson
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import com.planbase.pdf.layoutmanager.BorderStyle;
import com.planbase.pdf.layoutmanager.Cell;
import com.planbase.pdf.layoutmanager.CellStyle;
import com.planbase.pdf.layoutmanager.LineStyle;
import com.planbase.pdf.layoutmanager.LogicalPage;
import com.planbase.pdf.layoutmanager.Padding;
import com.planbase.pdf.layoutmanager.PdfLayoutMgr;
import com.planbase.pdf.layoutmanager.ScaledJpeg;
import com.planbase.pdf.layoutmanager.TextStyle;
import com.planbase.pdf.layoutmanager.Coord;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static com.planbase.pdf.layoutmanager.CellStyle.Align.*;
import static java.awt.Color.*;

public class TestManualllyPdfLayoutMgr {

//    public static void main(String... args) throws IOException, COSVisitorException {
//        new TestManualllyPdfLayoutMgr().testPdf();
//    }

    /** Just a convenience abbreviation for Arrays.asList() */
    private static <T> List<T> vec(T... ts) { return Arrays.asList(ts); }

    @Test
    public void testPdf() throws IOException {
        // Nothing happens without a PdfLayoutMgr.
        PdfLayoutMgr pageMgr = PdfLayoutMgr.newRgbPageMgr();

        // One inch is 72 document units.  40 is about a half-inch - enough margin to satisfy most
        // printers. A typical monitor has 72 dots per inch, so you can think of these as pixels
        // even though they aren't.  Things can be aligned right, center, top, or anywhere within
        // a "pixel".
        final double pMargin = 40;

        // A LogicalPage is a group of pages with the same settings.  When your contents scroll off
        // the bottom of a page, a new page is automatically created for you with the settings taken
        // from the LogicPage grouping. If you don't want a new page, be sure to stay within the
        // bounds of the current one!
        LogicalPage lp = pageMgr.logicalPageStart();

        // Set up some useful constants for later.
        final double tableWidth = lp.pageWidth() - (2 * pMargin);
        final double pageRMargin = pMargin + tableWidth;
        final double colWidth = tableWidth/4;
        final double[] colWidths = new double[] { colWidth + 10, colWidth + 10,
                                                colWidth + 10, colWidth - 30 };
        final Padding textCellPadding = Padding.of(2);

        // Set up some useful styles for later
        final TextStyle heading = TextStyle.of(PDType1Font.HELVETICA_BOLD, 9.5, WHITE);
        final CellStyle headingCell =
                CellStyle.of(BOTTOM_CENTER, textCellPadding, BLUE,
                             BorderStyle.builder()
                                        .left(LineStyle.of(BLUE))
                                        .right(LineStyle.of(WHITE))
                                        .build());
        final CellStyle headingCellR =
                CellStyle.of(BOTTOM_CENTER, textCellPadding, BLACK,
                             BorderStyle.builder()
                                        .left(LineStyle.of(WHITE))
                                        .right(LineStyle.of(BLACK))
                                        .build());

        final TextStyle regular = TextStyle.of(PDType1Font.HELVETICA, 9.5, BLACK);
        final CellStyle regularCell = CellStyle.of(TOP_LEFT, textCellPadding, null,
                                                   BorderStyle.builder()
                                                              .left(LineStyle.of(BLACK))
                                                              .right(LineStyle.of(BLACK))
                                                              .bottom(LineStyle.of(BLACK))
                                                              .build());

        // Let's draw three tables on our first landscape-style page grouping.

        // Draw the first table with lots of extra room to show off the vertical and horizontal
        // alignment.
        Coord xya =
                lp.tableBuilder(Coord.of(40.0, lp.yPageTop()))
                  .addCellWidths(vec(120.0, 120.0, 120.0))
                  .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12, YELLOW.brighter()))
                  .partBuilder().cellStyle(CellStyle.of(BOTTOM_CENTER, Padding.of(2),
                                                        decode("#3366cc"), BorderStyle.of(BLACK)))
                  .rowBuilder().addTextCells("First", "Second", "Third").buildRow()
                  .buildPart()
                  .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2),
                                                        decode("#ccffcc"),
                                                        BorderStyle.of(DARK_GRAY)))
                  .minRowHeight(120)
                  .textStyle(TextStyle.of(PDType1Font.COURIER, 12, BLACK))
                  .rowBuilder()
                  .cellBuilder().align(TOP_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(TOP_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(TOP_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .rowBuilder()
                  .cellBuilder().align(MIDDLE_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(MIDDLE_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(MIDDLE_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .rowBuilder()
                  .cellBuilder().align(BOTTOM_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(BOTTOM_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(BOTTOM_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .buildPart()
                  .buildTable();

        // The second table uses the x and y offsets from the previous table to position it to the
        // right of the first.
        Coord xyb =
                lp.tableBuilder(Coord.of(xya.getX() + 10, lp.yPageTop()))
                  .addCellWidths(vec(100.0, 100.0, 100.0))
                  .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12, YELLOW.brighter()))
                  .partBuilder().cellStyle(CellStyle.of(BOTTOM_CENTER, Padding.of(2),
                                                        decode("#3366cc"), BorderStyle.of(BLACK)))
                  .rowBuilder().addTextCells("First", "Second", "Third").buildRow()
                  .buildPart()
                  .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2),
                                                        decode("#ccffcc"),
                                                        BorderStyle.of(DARK_GRAY)))
                  .minRowHeight(100)
                  .textStyle(TextStyle.of(PDType1Font.COURIER, 12, BLACK))
                  .rowBuilder()
                  .cellBuilder().align(BOTTOM_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(BOTTOM_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(BOTTOM_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .rowBuilder()
                  .cellBuilder().align(MIDDLE_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(MIDDLE_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(MIDDLE_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .rowBuilder()
                  .cellBuilder().align(TOP_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(TOP_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .cellBuilder().align(TOP_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
                  .buildRow()
                  .buildPart()
                  .buildTable();

        // The third table uses the x and y offsets from the previous tables to position it to the
        // right of the first and below the second.  Negative Y is down.  This third table showcases
        // the way cells extend vertically (but not horizontally) to fit the text you put in them.
        lp.tableBuilder(Coord.of(xya.getX() + 10, xyb.getY() - 10))
          .addCellWidths(vec(100.0, 100.0, 100.0))
          .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12,
                                  YELLOW.brighter()))
          .partBuilder().cellStyle(CellStyle.of(BOTTOM_CENTER, Padding.of(2),
                                                decode("#3366cc"),
                                                BorderStyle.of(BLACK)))
          .rowBuilder().addTextCells("First", "Second", "Third").buildRow()
          .buildPart()
          .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2),
                                                decode("#ccffcc"),
                                                BorderStyle.of(DARK_GRAY)))
          .textStyle(TextStyle.of(PDType1Font.COURIER, 12, BLACK))
          .rowBuilder().cellBuilder().align(BOTTOM_RIGHT).addStrs("Line 1").buildCell()
          .cellBuilder().align(BOTTOM_CENTER).addStrs("Line 1", "Line two").buildCell()
          .cellBuilder().align(BOTTOM_LEFT)
          .addStrs("Line 1", "Line two", "[Line three is long enough to wrap]").buildCell()
          .buildRow()
          .rowBuilder().cellBuilder().align(MIDDLE_RIGHT).addStrs("Line 1", "Line two").buildCell()
          .cellBuilder().align(MIDDLE_CENTER).addStrs("").buildCell()
          .cellBuilder().align(MIDDLE_LEFT).addStrs("Line 1").buildCell().buildRow()
          .rowBuilder().cellBuilder().align(TOP_RIGHT).addStrs("L1").buildCell()
          .cellBuilder().align(TOP_CENTER).addStrs("Line 1", "Line two").buildCell()
          .cellBuilder().align(TOP_LEFT).addStrs("Line 1").buildCell().buildRow()
          .buildPart()
          .buildTable();

        lp.commit();

        // Let's do a portrait page now.  I just copied this from the previous page.
        lp = pageMgr.logicalPageStart(LogicalPage.Orientation.PORTRAIT);
        lp.tableBuilder(Coord.of(40, lp.yPageTop()))
          .addCellWidths(vec(120.0, 120.0, 120.0))
          .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12, YELLOW.brighter()))
          .partBuilder().cellStyle(CellStyle.of(BOTTOM_CENTER, Padding.of(2), decode("#3366cc"),
                                                BorderStyle.of(BLACK)))
          .rowBuilder().addTextCells("First", "Second", "Third").buildRow()
          .buildPart()
          .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2), decode("#ccffcc"),
                                                BorderStyle.of(DARK_GRAY))).minRowHeight(120)
          .textStyle(TextStyle.of(PDType1Font.COURIER, 12, BLACK))
          .rowBuilder()
          .cellBuilder().align(TOP_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(TOP_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(TOP_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .buildRow()
          .rowBuilder()
          .cellBuilder().align(MIDDLE_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(MIDDLE_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(MIDDLE_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .buildRow()
          .rowBuilder()
          .cellBuilder().align(BOTTOM_LEFT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(BOTTOM_CENTER).addStrs("Line 1", "Line two", "Line three").buildCell()
          .cellBuilder().align(BOTTOM_RIGHT).addStrs("Line 1", "Line two", "Line three").buildCell()
          .buildRow()
          .buildPart()
          .buildTable();

        // Where's the lower-right-hand corner?  Put a cell there.
        lp.tableBuilder(Coord.of(lp.pageWidth() - (100 + pMargin),
                                    lp.yPageBottom() + 15 + pMargin))
          .addCellWidths(vec(100.0))
          .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12,
                                  YELLOW.brighter()))
          .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2),
                                                decode("#3366cc"),
                                                BorderStyle.of(BLACK)))
          .rowBuilder().addTextCells("Lower-Right").buildRow()
          .buildPart()
          .buildTable();

        lp.commit();

        // More landscape pages
        lp = pageMgr.logicalPageStart();
        TextStyle pageHeadTextStyle = TextStyle.of(PDType1Font.HELVETICA, 7, BLACK);
        CellStyle pageHeadCellStyle = CellStyle.of(TOP_CENTER, null, null, null);

        lp.putCellAsHeaderFooter(pMargin, lp.yPageTop() + 10,
                                 Cell.of(pageHeadCellStyle, tableWidth, pageHeadTextStyle,
                                         "Test Logical Page Three"));

//        y = pageMgr.putRect(XyPair.of(pMargin, y), XyPair.of(100,100), Color.BLUE).y();

        // We're going to reset and reuse this y variable.
        double y = lp.yPageTop();

        y = lp.putRow(pMargin, y,
                      Cell.of(headingCell, colWidths[0], heading,
                              "Transliterated Russian (with un-transliterated Chinese below)"),
                      Cell.of(headingCellR, colWidths[1], heading, "US English"),
                      Cell.of(headingCellR, colWidths[2], heading, "Finnish"),
                      Cell.of(headingCellR, colWidths[3], heading, "German"));

        File f = new File("target/test-classes/melon.jpg");
        System.out.println(f.getAbsolutePath());
        BufferedImage melonPic = ImageIO.read(f);

        y = lp.putRow(pMargin, y,
                      Cell.builder(regularCell, colWidths[0])
                          .add(regular,
                               vec("This used to have Russian and Chinese text.",
                                   "The Russian was transliterated and the",
                                   "Chinese was turned into bullets.",
                                   "PDFBox 2.x, now handles many characters better,",
                                   "but throws exceptions for",
                                   "characters it doesn't understand.",
                                   "Truth be told, I don't understand so well how",
                                   "it works, but I think if you get an exception,",
                                   "you need to load a font like:",
                                   "PDFont font = PDTrueTypeFont.loadTTF(document, \"Arial.ttf\");",
                                   "See:",
                                   "https://pdfbox.apache.org/1.8/cookbook/",
                                   "workingwithfonts.html",
//                                   "???????????? ??? ?????????????????? ???????? ??????????????,",
//                                   "???????????? ??? ?????????????? ???????? ????????????.",
//                                   "?????????????? ????????, ?????????????? ?????????? ???",
//                                   "???????? ?????????????????? ???? ?????? ??????????????!",
//                                   null,
//                                   "Chorus:",
//                                   null,
//                                   "??????????????, ?????????????????? ???????? ??????????????????, ???????????????? ?????????????? ????????" +
//                                   " ??????????????, ???????????????? ???????????? ???????????????? ????????????????! ??????????????, ????????????!" +
//                                   " ???? ???????????????? ??????????!",
//                                   null,
//                                   "???? ?????????? ?????????? ???? ?????????????????? ???????? ?????????????????????? ???????? ???????? ??" +
//                                   " ????????. ???????? ???? ???? ??????????! ???????? ???? ?????????? ??? ???????????????? ?????????? " +
//                                   "???????????? ??????????!",
//                                   null,
//                                   "Chorus:",
//                                   null,
//                                   "?????????????? ?????????????? ?????? ?????????? ?? ?????? ??????????",
//                                   "???????????????? ?????? ?????????????????? ????????.",
//                                   "?????? ???????? ???????? ???????? ???????????????? ??????????????.",
//                                   "?????? ????????, ?????? ???????? ?? ?????? ?????????? ????????????!",
//                                   null,
//                                   "Chorus",
//                                   null,
//                                   null,
//                                   null,
//                                   "Chinese will not print.  The substitution character is a" +
//                                   " bullet, so below should be lots of bullets.",
//                                   null,
//                                   "???????????????????????????????????? " +
//                                   "???????????????????????????????????????????????? " +
//                                   "??????????????????????????????????????? " +
//                                   "?????????????????????????????????????????? " +
//                                   "??????????????????????????? " +
//                                   "????????????????????? " +
//                                   "????????????????????????????????? " +
//                                   "????????????????????????????????? " +
//                                   "????????????????????????",
                                   null,
                                   "Here is a picture with the default and other sizes.  Though" +
                                   " it shows up several times, the image data is only attached" +
                                   " to the file once and reused."))
                          .addAll(vec(ScaledJpeg.of(melonPic),
                                      ScaledJpeg.of(melonPic, 50, 50),
                                      ScaledJpeg.of(melonPic, 50, 50),
                                      ScaledJpeg.of(melonPic, 170, 100)))
                          .build(),
                      Cell.builder(regularCell, colWidths[1])
                          .add(regular,
                               // Flowing text
                               vec("O say can you see by the dawn's early light, " +
                                   "What so proudly we hailed at the twilight's last gleaming, " +
                                   "Whose broad stripes and bright stars " +
                                   "through the perilous fight, " +
                                   "O'er the ramparts we watched, were so gallantly streaming? " +
                                   "And the rockets' red glare, the bombs bursting in air, " +
                                   "Gave proof through the night that our flag was still there; " +
                                   "O say does that star-spangled banner yet wave, " +
                                   "O'er the land of the free and the home of the brave? ",
                                   // Tiny space
                                   null,
                                   // Set line breaks:
                                   "On the shore dimly seen through the mists of the deep, ",
                                   "Where the foe's haughty host in dread silence reposes, ",
                                   "What is that which the breeze, o'er the towering steep, ",
                                   "As it fitfully blows, half conceals, half discloses? ",
                                   "Now it catches the gleam of the morning's first beam, ",
                                   "In full glory reflected now shines in the stream: ",
                                   "'Tis the star-spangled banner, O! long may it wave ",
                                   "O'er the land of the free and the home of the brave. ",
                                   // Big space.
                                   null,
                                   null,
                                   null,
                                   null,
                                   null,
                                   null,
                                   null,
                                   null,
                                   // Flowing text
                                   "And where is that band who so vauntingly swore " +
                                   "That the havoc of war and the battle's confusion, " +
                                   "A home and a country, should leave us no more? " +
                                   "Their blood has washed out their foul footsteps' pollution. " +
                                   "No refuge could save the hireling and slave " +
                                   "From the terror of flight, or the gloom of the grave: " +
                                   "And the star-spangled banner in triumph doth wave, " +
                                   "O'er the land of the free and the home of the brave. " +
                                   null,
                                   "O thus be it ever, when freemen shall stand " +
                                   "Between their loved home and the war's desolation. " +
                                   "Blest with vict'ry and peace, may the Heav'n rescued land " +
                                   "Praise the Power that hath made and preserved us a nation! " +
                                   "Then conquer we must, when our cause it is just, " +
                                   "And this be our motto: \"In God is our trust.\" " +
                                   "And the star-spangled banner in triumph shall wave " +
                                   "O'er the land of the free and the home of the brave!"))
                          .build(),
                      Cell.builder(regularCell, colWidths[2])
                          .add(regular,
                               vec("Maamme",
                                   null,
                                   "Monument to the V??rt Land poem in Helsinki. " +
                                   "Oi maamme, Suomi, synnyinmaa, " +
                                   "soi, sana kultainen! " +
                                   "Ei laaksoa, ei kukkulaa, " +
                                   "ei vett??, rantaa rakkaampaa " +
                                   "kuin kotimaa t???? pohjoinen, " +
                                   "maa kallis isien. " +
                                   "Sun kukoistukses kuorestaan " +
                                   "kerrankin puhkeaa; " +
                                   "viel' lempemme saa nousemaan " +
                                   "sun toivos, riemus loistossaan, " +
                                   "ja kerran laulus, synnyinmaa " +
                                   "korkeemman kaiun saa. ",
                                   null,
                                   "V??rt land ",
                                   null,
                                   "(the original, by Johan Ludvig Runeberg) " +
                                   "V??rt land, v??rt land, v??rt fosterland, " +
                                   "ljud h??gt, o dyra ord! " +
                                   "Ej lyfts en h??jd mot himlens rand, " +
                                   "ej s??nks en dal, ej sk??ljs en strand, " +
                                   "mer ??lskad ??n v??r bygd i nord, " +
                                   "??n v??ra f??ders jord! " +
                                   "Din blomning, sluten ??n i knopp, " +
                                   "Skall mogna ur sitt tv??ng; " +
                                   "Se, ur v??r k??rlek skall g?? opp " +
                                   "Ditt ljus, din glans, din fr??jd, ditt hopp. " +
                                   "Och h??gre klinga skall en g??ng " +
                                   "V??r fosterl??ndska s??ng."))
                          .build(),
                      Cell.builder(regularCell, colWidths[3])
                          .add(regular,
                               vec(// Older first 2 verses obsolete.
                                   "Einigkeit und Recht und Freiheit " +
                                   "F??r das deutsche Vaterland! " +
                                   "Danach lasst uns alle streben " +
                                   "Br??derlich mit Herz und Hand! " +
                                   "Einigkeit und Recht und Freiheit " +
                                   "Sind des Gl??ckes Unterpfand;" +
                                   "Bl??h' im Glanze dieses Gl??ckes, " +
                                   "  Bl??he, deutsches Vaterland!"))
                          .build());

        lp.putRow(pMargin, y,
                  Cell.of(regularCell, colWidths[0], regular, "Another row of cells"),
                  Cell.of(regularCell, colWidths[1], regular, "On the second page"),
                  Cell.of(regularCell, colWidths[2], regular, "Just like any other page"),
                  Cell.of(regularCell, colWidths[3], regular, "That's it!"));
        lp.commit();

        final LineStyle lineStyle = LineStyle.of(BLACK, 1);

        lp = pageMgr.logicalPageStart();

        lp.putCellAsHeaderFooter(pMargin, lp.yPageTop() + 10,
                                 Cell.of(pageHeadCellStyle, tableWidth, pageHeadTextStyle,
                                         "Test Logical Page Four"));

        // Make a big 3-page X in a box.  Notice that we code it as though it's on one page, and the
        // API adds two more pages as needed.  This is a great test for how geometric shapes break
        // across pages.

        // top lne
        lp.putLine(pMargin, lp.yPageTop(), pageRMargin, lp.yPageTop(), lineStyle);
        // left line
        lp.putLine(pMargin, lp.yPageTop(), pMargin, -lp.yPageTop(), lineStyle);
        // 3-page-long X
        lp.putLine(pMargin, lp.yPageTop(), pageRMargin, -lp.yPageTop(), lineStyle);
        // middle line
        lp.putLine(pMargin, 0, pageRMargin, 0, lineStyle);
        lp.putLine(pageRMargin, lp.yPageTop(), pMargin, -lp.yPageTop(), lineStyle);
        // right line
        lp.putLine(pageRMargin, lp.yPageTop(), pageRMargin, -lp.yPageTop(), lineStyle);
        // bottom line
        lp.putLine(pMargin, -lp.yPageTop(), pageRMargin, -lp.yPageTop(), lineStyle);
        lp.commit();

        // All done - write it out!

        // In a web application, this could be:
        //
        // httpServletResponse.setContentType("application/pdf") // your server may do this for you.
        // os = httpServletResponse.getOutputStream()            // you probably have to do this
        //
        // Also, in a web app, you probably want name your action something.pdf and put
        // target="_blank" on your link to the PDF download action.

        // We're just going to write to a file.
        OutputStream os = new FileOutputStream("test.pdf");

        // Commit it to the output stream!
        pageMgr.save(os);
    }
}