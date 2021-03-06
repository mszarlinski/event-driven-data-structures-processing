package pl.mszarlinski.algo.eddsp.impl;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static pl.mszarlinski.algo.eddsp.api.TreeWalker.aTreeWalker;
import pl.mszarlinski.algo.eddsp.api.Parameter;
import pl.mszarlinski.algo.eddsp.api.TraverseResult;
import pl.mszarlinski.algo.eddsp.core.TreeNode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class IsTreeBSTAlgorithmTest {
    @DataProvider(name = "data")
    public static Object[][] dataProvider() {
        return new Object[][]{
            {
                new int[]{0},
                new int[]{1},
                0,
                true
            },
            {
                new int[]{0, 0, 1, 1, 2, 2, 3, 7},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
                0,
                true
            },
            {
                new int[]{0, 0, 1, 1, 2, 3, 2, 3, 3},
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                0,
                false
            }
        };
    }

    @Test(dataProvider = "data")
    public void shouldTestWhetherTreeIsBST(final int[] from, final int[] to, final int rootId, final boolean expected) {
        // given
        final Parameter<Boolean> isBST = new Parameter<>("isBST");
        // when
        final TraverseResult result = aTreeWalker()
            .from(from, to, rootId)
            .withLogging(true)
            .onLeaf((leaf, ctx) -> isBST.put(leaf.getData(), true))
            .onBottomUpNode((node, ctx) ->
                    isBST.put(node.getData(),
                        node.getChildren().size() <= 2 &&
                        node.getChildren().stream().map(TreeNode::getData).allMatch(isBST::get)
                    )
            )
            .traverse();
        // then
        assertThat(isBST.get(result.getRootNode().getData())).isEqualTo(expected);

    }
}
