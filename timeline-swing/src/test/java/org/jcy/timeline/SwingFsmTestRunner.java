package org.jcy.timeline;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.*;
import org.jcy.timeline.swing.TimelineSwingFsm;
import org.junit.Test;

import java.io.IOException;

public class SwingFsmTestRunner {

    @Test
    public void test() throws IOException {
        TimelineSwingFsm test = TimelineSwingFsm.getInstance();

        GreedyTester tester = new GreedyTester(test);
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.setResetProbability(0.005);
        GraphListener graphListener = tester.buildGraph(100000);
        tester.printCoverage();
        graphListener.printGraphDot("timeline-swing-2.dot");

    }
}
