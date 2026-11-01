import ru.gr05504.domain.CalculationRepository;
import ru.gr05504.domain.FormulaParser;
import ru.gr05504.domain.IntegralCalculator;
import ru.gr05504.domain.Integrator;
import ru.gr05504.infrastructure.InMemoryCalculationRepository;
import ru.gr05504.infrastructure.Exp4jFormulaParser;
import ru.gr05504.application.CalculationService;
import ru.gr05504.ui.CalculatorWindow;

void main() {
    CalculationRepository repository =
            new InMemoryCalculationRepository();

    FormulaParser parser =
            new Exp4jFormulaParser();

    IntegralCalculator integrator =
            new Integrator();

    CalculationService service = new CalculationService(
            repository,
            parser,
            integrator
    );
    new CalculatorWindow(service).setVisible(true);
} //