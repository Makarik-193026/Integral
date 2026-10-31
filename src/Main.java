import ru.gr05504.application.CalculationService;
import ru.gr05504.infrastructure.Exp4jFormulaParser;
import ru.gr05504.infrastructure.InMemoryCalculationRepository;
import ru.gr05504.ui.CalculatorWindow;

void main() {
    CalculationService service = new CalculationService(
            new InMemoryCalculationRepository(),
            new Exp4jFormulaParser()
    );
    new CalculatorWindow(service).setVisible(true);
}