package com.javarush.island.zolotarev.island.view.console;

import com.javarush.island.zolotarev.island.api.view.View;
import com.javarush.island.zolotarev.island.config.OrganismRegistry;
import com.javarush.island.zolotarev.island.config.SimulationConfig;
import com.javarush.island.zolotarev.island.entity.map.Island;
import com.javarush.island.zolotarev.island.entity.map.Location;
import com.javarush.island.zolotarev.island.entity.organisms.Organism;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.javarush.island.zolotarev.island.view.console.Symbols.*;

public final class ConsoleView implements View {

    public static final ConsoleView INSTANCE = new ConsoleView();

    private static final int CELL_CHARS = 4;

    private ConsoleView() {
    }

    @Override
    public void show(long tick, Island island) {
        System.out.println();
        System.out.printf("Tick %d%n", tick);
        showMap(island);
        showStatistics(island);
    }

    private void showMap(Island island) {
        int showRows = SimulationConfig.SHOW_ROWS;
        int showCols = SimulationConfig.SHOW_COLS;

        int rows = Math.min(island.getHeight(), showRows);
        int cols = Math.min(island.getWidth(), showCols);
        boolean cutRows = island.getHeight() > showRows;
        boolean cutCols = island.getWidth() > showCols;

        String borderSegment = "═".repeat(CELL_CHARS);
        String topBorder = border(cols, borderSegment, LEFT_TOP, TOP, RIGHT_TOP, cutCols);
        String centerBorder = border(cols, borderSegment, LEFT, CENTER, RIGHT, cutCols);
        String bottomBorder = border(cols, borderSegment, LEFT_BOTTOM, CENTER_BOTTOM, RIGHT_BOTTOM, cutCols);
        int rowChars = cols * (CELL_CHARS + 1) + 1;
        String bottomInfBorder = String.valueOf(INF_MARGIN).repeat(rowChars);

        StringBuilder out = new StringBuilder();
        for (int y = 0; y < rows; y++) {
            out.append(y == 0 ? topBorder : centerBorder).append(LINE_BREAK);
            for (int x = 0; x < cols; x++) {
                out.append(formatCell(cellContent(island.getLocation(x, y))));
            }
            out.append(cutCols ? INF_MARGIN : CELL_MARGIN).append(LINE_BREAK);
        }
        out.append(cutRows ? bottomInfBorder : bottomBorder).append(LINE_BREAK);
        System.out.print(out);
    }

    private String cellContent(Location location) {
        List<Map.Entry<Class<? extends Organism>, Integer>> grouped = snapshotGroupedCounts(location);
        if (grouped.isEmpty()) {
            return DOT.repeat(CELL_CHARS);
        }
        String icon = firstIcon(OrganismRegistry.getIcon(grouped.get(0).getKey()));
        return centerInCell(icon, " ");
    }

    private String firstIcon(String icon) {
        if (icon.isEmpty()) {
            return "?";
        }
        int end = icon.offsetByCodePoints(0, 1);
        return icon.substring(0, end);
    }

    private String centerInCell(String icon, String pad) {
        int iconChars = icon.length();
        if (iconChars >= CELL_CHARS) {
            return icon.substring(0, CELL_CHARS);
        }
        int padding = CELL_CHARS - iconChars;
        int left = padding / 2;
        int right = padding - left;
        return pad.repeat(left) + icon + pad.repeat(right);
    }

    private String formatCell(String content) {
        if (content.length() != CELL_CHARS) {
            content = fitToCell(content);
        }
        return CELL_MARGIN + content;
    }

    private String fitToCell(String content) {
        if (content.length() >= CELL_CHARS) {
            return content.substring(0, CELL_CHARS);
        }
        return content + DOT.repeat(CELL_CHARS - content.length());
    }

    private List<Map.Entry<Class<? extends Organism>, Integer>> snapshotGroupedCounts(Location location) {
        Map<Class<? extends Organism>, Integer> counts = new HashMap<>();
        synchronized (location) {
            for (Organism organism : location.getAllOrganisms()) {
                if (organism.isAlive()) {
                    counts.merge(organism.getClass(), 1, Integer::sum);
                }
            }
        }
        List<Map.Entry<Class<? extends Organism>, Integer>> grouped = new ArrayList<>(counts.entrySet());
        grouped.sort(Comparator.comparingInt(Map.Entry<Class<? extends Organism>, Integer>::getValue).reversed());
        return grouped;
    }

    private void showStatistics(Island island) {
        Map<Class<? extends Organism>, Integer> stats = island.getStatistics();
        String line = stats.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Class::getSimpleName)))
                .map(e -> OrganismRegistry.getIcon(e.getKey()) + "=" + e.getValue())
                .collect(Collectors.joining(", "));
        System.out.printf("Statistics: %s | total=%d%n", line, island.getTotalOrganismCount());
    }

    private String border(int cols, String borderSegment, char left, char center, char right, boolean cutCols) {
        char rightChar = cutCols ? INF_MARGIN : right;
        return IntStream.range(0, cols)
                .mapToObj(col -> (col == 0 ? left : center) + borderSegment)
                .collect(Collectors.joining(BLANK, BLANK, String.valueOf(rightChar)));
    }
}
