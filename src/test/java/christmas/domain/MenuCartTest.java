package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuCartTest {
    private MenuCart cart;

    @BeforeEach
    void testInit() {
        cart = new MenuCart();
    }

    @DisplayName("장바구니에 메뉴가 추가 된다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타", "초코케이크", "아이스크림"})
    void testAddMenu(String menuName) {
        assertThatNoException().isThrownBy(() -> {
            cart.addMenu(new Menu(menuName), 1);
        });
    }

    @DisplayName("장바구니에 중복되는 메뉴를 추가 할 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타", "초코케이크", "아이스크림"})
    void testAddMenuDuplicateExceptionCheck(String menuName) {
        cart.addMenu(new Menu(menuName), 1);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu(menuName), 1);
        });
    }

    @DisplayName("장바구니에 최소 갯수 미만의 메뉴를 추가 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuMinOrderExceptionCheck() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu("양송이수프"), 0);
        });
    }

    @DisplayName("장바구니에 20개 이상의 메뉴를 추가 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuMaxOrderExceptionCheck() {
        cart.addMenu(new Menu("양송이수프"), 19);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu("양송이수프"), 1);
        });
    }

    @DisplayName("장바구니에 null을 전달 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuNullExceptionCheck() {
        assertThatNullPointerException().isThrownBy(() -> {
            cart.addMenu(null, 1);
        });
    }

    @DisplayName("장바구니에 담았던 메뉴 갯수와 장바구니에 담겨 있는 메뉴 갯수가 동일하다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타", "초코케이크", "아이스크림"})
    void testGetMenus(String menuName) {
        Integer expectedCount = 2;

        Menu menu = new Menu(menuName);
        cart.addMenu(menu, expectedCount);
        Integer actual = cart.getMenus().get(menu);

        assertThat(actual).isEqualTo(expectedCount);
    }
}