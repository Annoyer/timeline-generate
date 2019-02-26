package org.jcy.timeline.core;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FsmTestHelper {

    private static final Logger log = LoggerFactory.getLogger(FsmTestHelper.class);

    private static final File RECORD_DIR = new File("E:\\@文件\\大学\\@毕业\\毕业论文\\data");
    private static final String FILE_SUFFIX = ".dot";

    private static final String DEFAULT_SUB_DIR = "timeline-core";

    public static void runTest(FsmModel fsm, String graphFileName) {
        runTest(fsm, DEFAULT_SUB_DIR, graphFileName);
    }

    public static void runTest(FsmModel fsm, String subBir, String graphFileName) {
        Model model = new Model(fsm);

        File dir = RECORD_DIR;
        if (subBir != null && !subBir.isEmpty()) {
            dir = new File(RECORD_DIR, subBir);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }
        }


        if (new File(dir, graphFileName).exists()) {
            graphFileName = graphFileName.replace(FILE_SUFFIX, System.currentTimeMillis() + FILE_SUFFIX);
        }
        try {
            String graphPath = new File(dir, graphFileName).getCanonicalPath();
            File detailFile = new File(dir, graphFileName.replace(FILE_SUFFIX, "-detail" + FILE_SUFFIX));

            if (!detailFile.exists()) {
                detailFile.createNewFile();
            }
            model.setOutput(new PrintWriter(detailFile));

            GreedyTester tester = new GreedyTester(model);
            tester.addListener(new StopOnFailureListener());
            tester.addListener(new VerboseListener());
            tester.addCoverageMetric(new TransitionCoverage());
            tester.addCoverageMetric(new TransitionPairCoverage());
            tester.addCoverageMetric(new ActionCoverage());
            tester.addCoverageMetric(new StateCoverage());
            GraphListener graphListener = tester.buildGraph(100000);
            tester.printCoverage();

            graphListener.printGraphDot(graphPath);
        } catch (IOException e) {
            log.error("Fail to print fsm graph.", e);
        }
    }
}
