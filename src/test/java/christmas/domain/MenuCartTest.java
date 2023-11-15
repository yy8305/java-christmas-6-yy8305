package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import christmas.constants.MenuType;
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
    void testAddMenuHandlesDuplicateException(String menuName) {
        cart.addMenu(new Menu(menuName), 1);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu(menuName), 1);
        });
    }

    @DisplayName("장바구니에 최소 갯수 미만의 메뉴를 추가 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuHandlesMinOrderException() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu("양송이수프"), 0);
        });
    }

    @DisplayName("장바구니에 20개 이상의 메뉴를 추가 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuHandlesMaxOrderException() {
        cart.addMenu(new Menu("양송이수프"), 19);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            cart.addMenu(new Menu("양송이수프"), 1);
        });
    }

    @DisplayName("장바구니에 null을 전달 할 경우 예외가 발생한다.")
    @Test
    void testAddMenuHandlesNullException() {
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

    @DisplayName("장바구니에 담았던 애피타이저 메뉴 개수가 3개 이다.")
    @Test
    void testMenuTypeCount() {
        final Integer expectedCount = 3;
        cart.addMenu(new Menu("양송이수프"), 1);
        cart.addMenu(new Menu("타파스"), 2);

        Integer actual = cart.getMenuTypeCount(MenuType.APPETIZER);

        assertThat(actual).isEqualTo(expectedCount);
    }

    @DisplayName("장바구니에 담긴 메뉴의 총 주문 금액은 17,000원 이다.")
    @Test
    void testGetTotalOrderMenu() {
        final Integer expectedCount = 17_000;
        cart.addMenu(new Menu("양송이수프"), 1);
        cart.addMenu(new Menu("타파스"), 2);

        Integer actual = cart.getTotalOrderAmount();

        assertThat(actual).isEqualTo(expectedCount);
    }

    @DisplayName("사용자가 입력한 메뉴 리스트를 통해 생성한 메뉴 장바구니의 크기는 입력한 메뉴 개수와 동일하다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,제로콜라-1", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1"})
    void testCreateCartFromUserInput(String input) {
        Integer expectedSize = input.split(",").length;

        MenuCart cart = MenuCart.from(input);

        assertThat(cart.getMenus().size()).isEqualTo(expectedSize);
    }

    @DisplayName("사용자가 입력한 메뉴 리스트에서 개수가 숫자가 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-a,제로콜라-b", "티본스테이크-@"})
    void testCreateCartFromUserInputHandlesNumericException(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            MenuCart.from(input);
        });
    }

    @DisplayName("사용자가 입력한 메뉴 리스트에서 개수가 1미만일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스--1", "티본스테이크-0"})
    void testCreateCartFromUserInputHandlesMinCountException(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            MenuCart.from(input);
        });
    }

    @DisplayName("사용자가 입력한 메뉴 리스트에서 메뉴가 중복될 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1,타파스-1", "티본스테이크-1,티본스테이크-2"})
    void testCreateCartFromUserInputHandlesDuplicateException(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            MenuCart.from(input);
        });
    }

    @DisplayName("사용자가 입력한 메뉴 리스트가 메뉴판에 없는 메뉴일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"햄버거-1,피자-1", "빅맥-12"})
    void testCreateCartFromUserInputHandlesPresenceOnTheMenuException(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            MenuCart.from(input);
        });
    }

    @DisplayName("사용자가 입력한 메뉴 리스트가 형식에 맞지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"타파스1,제로콜라-1", ",티본스테이크-1,바비큐립-1", "-,-", "-1,-2"})
    void testCreateCartFromUserInputHandlesMenuListPatternException(String input) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            MenuCart.from(input);
        });
    }
}