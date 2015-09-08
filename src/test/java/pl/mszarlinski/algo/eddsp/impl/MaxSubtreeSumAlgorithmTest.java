package pl.mszarlinski.algo.eddsp.impl;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static pl.mszarlinski.algo.eddsp.api.TreeWalker.aTreeWalker;
import pl.mszarlinski.algo.eddsp.api.Parameter;
import pl.mszarlinski.algo.eddsp.api.TraverseResult;
import pl.mszarlinski.algo.eddsp.core.TreeNode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tree version of Kadane's algorithm. Created by mszarlinski on 2015-08-23.
 */
public class MaxSubtreeSumAlgorithmTest {
    @DataProvider(name = "data")
    public static Object[][] dataProvider() {
        return new Object[][]{
            {
                new int[]{0},
                new int[]{1},
                new int[]{-1, 2},
                0,
                2
            },
            {
                new int[]{0},
                new int[]{1},
                new int[]{3, 2},
                0,
                5
            },
            {
                new int[]{0, 0, 1, 1, 2, 2, 4, 4},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
                new int[]{-2, 1, -3, 2, 1, 1, 2, -1, 2},
                0,
                5
            }
        };
    }

    @Test(dataProvider = "data")
    public void shouldFindBestSumOfSubtrees(final int[] from, final int[] to, final int[] value, final int rootId, final int expected) {
        // given
        final Parameter<Integer> currentMaxSumParam = new Parameter<>("maxSum");
        final Parameter<Integer> sumOfSubtreeParam = new Parameter<>("sum");
        // when
        final TraverseResult result = aTreeWalker()
            .from(from, to, value, rootId)
            .withLogging(true)
            .onLeaf((leaf, ctx) -> {
                currentMaxSumParam.put(leaf.getData(), leaf.getValue().get());
                sumOfSubtreeParam.put(leaf.getData(), leaf.getValue().get());
            })
            .onBottomUpNode((node, ctx) -> {
                    int maxOfSubtrees = 0;
                    int sumOfSubtrees = node.getValue().get();

                    for (final TreeNode child : node.getChildren()) {
                        final int sumOfSubtree = sumOfSubtreeParam.get(child.getData());
                        final int maxAmongSubtrees = currentMaxSumParam.get(child.getData());
                        maxOfSubtrees = Integer.max(maxOfSubtrees, maxAmongSubtrees);
                        sumOfSubtrees += sumOfSubtree;

                    }

                    currentMaxSumParam.put(node.getData(), Integer.max(maxOfSubtrees, sumOfSubtrees));
                    sumOfSubtreeParam.put(node.getData(), sumOfSubtrees);
                }
            )
            .traverse();
        // then
        assertThat(currentMaxSumParam.get(result.getRootNode().getData())).isEqualTo(expected);

    }
}
