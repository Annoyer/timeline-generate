package org.jcy.timeline;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.*;
import org.jcy.timeline.swing.TimelineSwingFsm;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SwingFsmTestRunner {

    private static final File RECORD_DIR = new File("E:\\@文件\\大学\\@毕业\\毕业论文\\data");
    private static final String FILE_SUFFIX = ".dot";

    private static final String DEFAULT_SUB_DIR = "timeline-swing";

    @Test
    public void test() throws IOException {
        TimelineSwingFsm test = TimelineSwingFsm.getInstance();

        Model model = new Model(test);
        File dir = new File(RECORD_DIR, DEFAULT_SUB_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }

        String graphFileName = "timeline-swing.dot";

        if (new File(dir, graphFileName).exists()) {
            graphFileName = graphFileName.replace(FILE_SUFFIX, System.currentTimeMillis() + FILE_SUFFIX);
        }
        String graphPath = new File(dir, graphFileName).getCanonicalPath();
        File detailFile = new File(dir, graphFileName.replace(FILE_SUFFIX, "-detail" + FILE_SUFFIX));

        if (!detailFile.exists()) {
            detailFile.createNewFile();
        }
        model.setOutput(new PrintWriter(detailFile));
        GreedyTester tester = new GreedyTester(test);
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.setResetProbability(0.005);
        GraphListener graphListener = tester.buildGraph(10000);
        tester.printCoverage();
        graphListener.printGraphDot(graphPath);

    }
}
