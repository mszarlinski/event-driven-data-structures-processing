package pl.mszarlinski.algo.eddsp.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.mszarlinski.algo.eddsp.api.Parameter;
import pl.mszarlinski.algo.eddsp.api.TraverseResult;
import pl.mszarlinski.algo.eddsp.core.TreeNode;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static pl.mszarlinski.algo.eddsp.api.TreeWalker.aTreeWalker;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class MaxSubtreeSumAlgorithmTest {
    @DataProvider(name = "data")
    public static Object[][] dataProvider() {
        return new Object[][]{
                {
                        new int[]{0},
                        new int[]{1},
                        new int[]{0, 2},
                        0,
                        2
                },
                {
                        new int[]{0},
                        new int[]{1},
                        new int[]{4, 2},
                        0,
                        4
                }
                //TODO: more cases, fix tests
        };
    }

    @Test(dataProvider = "data")
    public void shouldFindBestSumOfSubtrees(final int[] from, final int[] to, final int[] value, final int rootId, final int expected) {
        // given
        final Parameter<Integer> maxSumOfSubtreeParam = new Parameter<>("sum");
        // when
        final TraverseResult result = aTreeWalker()
                .from(from, to, value, rootId)
                .withLogging(true)
                .onLeaf((leaf, ctx) -> maxSumOfSubtreeParam.put(leaf.getData(), 0))
                .onBottomUpNode((node, ctx) -> {
                            int maxOfSubtrees = 0;
                            int sumOfSubtrees = 0;

                            for (final TreeNode child : node.getChildren()) {
                                final int subtree = maxSumOfSubtreeParam.get(child.getData());
                                maxOfSubtrees = Integer.max(maxOfSubtrees, subtree);
                                sumOfSubtrees += subtree;

                            }

                            maxSumOfSubtreeParam.put(node.getData(), Integer.max(maxOfSubtrees, sumOfSubtrees + node.getValue().get()));
                        }
                )
                .traverse();
        // then
        assertThat(maxSumOfSubtreeParam.get(result.getRootNode().getData())).isEqualTo(expected);

    }
}
