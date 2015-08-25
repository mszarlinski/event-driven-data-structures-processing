package pl.mszarlinski.algo.eddsp.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.mszarlinski.algo.eddsp.api.TreeWalker.aTreeWalker;
import pl.mszarlinski.algo.eddsp.api.TraverseResult;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class CountingLeavesAlgorithmTest {
    @DataProvider(name = "data")
    public static Object[][] dataProvider() {
        return new Object[][]{
                {
                        new int[]{0, 0, 1, 1, 2},
                        new int[]{1, 2, 3, 4, 5},
                        0,
                        3
                },
                {
                        new int[]{0, 1, 2, 3, 4, 5},
                        new int[]{1, 2, 3, 4, 5, 6},
                        0,
                        1
                },
                {
                        new int[]{0},
                        new int[]{1},
                        0,
                        1
                }
        };
    }

    @Test(dataProvider = "data")
    public void shouldCountLeaves(final int[] from, final int[] to, final int rootId, final int expected) {
        // when
        final TraverseResult result = aTreeWalker()
                .from(from, to, rootId)
                .withLogging(true)
                .onLeaf((leaf, ctx) -> ctx.merge("leavesCount", 1, (counter, x) -> (Integer) counter + 1))
                .traverse();
        // then
        assertThat(result.getProcessingContext().get("leavesCount")).isEqualTo(expected);

    }
}
