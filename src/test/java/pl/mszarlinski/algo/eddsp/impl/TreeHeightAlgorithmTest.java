package pl.mszarlinski.algo.eddsp.impl;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static pl.mszarlinski.algo.eddsp.api.TreeWalker.aTreeWalker;
import pl.mszarlinski.algo.eddsp.api.Parameter;
import pl.mszarlinski.algo.eddsp.api.TraverseResult;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class TreeHeightAlgorithmTest {
    @DataProvider(name = "data")
    public static Object[][] dataProvider() {
        return new Object[][]{
            {
                new int[]{0},
                new int[]{1},
                0,
                1
            },
            {
                new int[]{0, 0, 1, 1, 2, 2, 3, 7},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
                0,
                4
            },
            {
                new int[]{0, 0, 1, 1, 2, 3, 2, 3, 3},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                0,
                3
            },
            {
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                0,
                10
            }
        };
    }

    @Test(dataProvider = "data")
    public void shouldFindTreeHeight(final int[] from, final int[] to, final int rootId, final int expected) {
        // given
        final Parameter<Integer> height = new Parameter<>("height");
        // when
        final TraverseResult result = aTreeWalker()
            .from(from, to, rootId)
            .withLogging(true)
            .onLeaf((leaf, ctx) -> height.put(leaf.getData(), 0))
            .onBottomUpNode((node, ctx) ->
                    height.put(node.getData(),
                        node.getChildren()
                            .stream()
                            .mapToInt(child -> height.get(child.getData()))
                            .max()
                            .getAsInt() + 1)
            )
            .traverse();
        // then
        assertThat(height.get(result.getRootNode().getData())).isEqualTo(expected);

    }
}
