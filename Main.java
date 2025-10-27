import functions.*;

public class Main {
    
    private static void testFunctionExceptions(TabulatedFunction function) {
        System.out.println("\nТестирование исключений:");
        
        try {
            function.getPoint(-1);
            System.out.println("ОШИБКА: Ожидалось FunctionPointIndexOutOfBoundsException");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("FunctionPointIndexOutOfBoundsException: " + e.getMessage());
        }
        
        try {
            function.getPoint(100);
            System.out.println("ОШИБКА: Ожидалось FunctionPointIndexOutOfBoundsException");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("FunctionPointIndexOutOfBoundsException: " + e.getMessage());
        }
        
        try {
            function.setPointX(2, function.getPointX(0) - 1);
            System.out.println("ОШИБКА: Ожидалось InappropriateFunctionPointException");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("InappropriateFunctionPointException: " + e.getMessage());
        }
        
        try {
            function.addPoint(new FunctionPoint(function.getPointX(1), 10.0));
            System.out.println("ОШИБКА: Ожидалось InappropriateFunctionPointException");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("InappropriateFunctionPointException: " + e.getMessage());
        }
        
        TabulatedFunction tempFunction = new ArrayTabulatedFunction(0, 2, 2);
        try {
            tempFunction.deletePoint(0);
            System.out.println("ОШИБКА: Ожидалось IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: " + e.getMessage());
        }
        
        try {
            function.addPoint(new FunctionPoint(2.5, 10.0));
            System.out.println("Точка успешно добавлена");
            
            function.setPointY(1, 15.0);
            System.out.println("Y-координата успешно изменена");
            
        } catch (InappropriateFunctionPointException e) {
            System.out.println("ОШИБКА: " + e.getMessage());
        }
    }
    
    private static void testTabulatedFunction(String functionName, TabulatedFunction function) {
        System.out.println("Тестирование: " + functionName);
        System.out.println("Область определения: [" + 
            function.getLeftDomainBorder() + ", " + function.getRightDomainBorder() + "]");
        System.out.println("Количество точек: " + function.getPointsCount());
        
        System.out.println("Точки функции:");
        for (int i = 0; i < function.getPointsCount(); i++) {
            FunctionPoint point = function.getPoint(i);
            System.out.printf("  [%d] (%.2f, %.2f)%n", i, point.getX(), point.getY());
        }
        
        System.out.println("Значения функции:");
        double left = function.getLeftDomainBorder();
        double right = function.getRightDomainBorder();
        for (double x = left; x <= right; x += (right - left) / 4) {
            double y = function.getFunctionValue(x);
            System.out.printf("  f(%.2f) = %.2f%n", x, y);
        }
        
        testFunctionExceptions(function);
    }
    
    private static void testConstructorExceptions() {
        System.out.println("Тестирование исключений в конструкторах:");
        
        try {
            new ArrayTabulatedFunction(10, 0, 5);
            System.out.println("ОШИБКА: Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        
        try {
            new LinkedListTabulatedFunction(5, 5, 5);
            System.out.println("ОШИБКА: Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        
        try {
            new ArrayTabulatedFunction(0, 10, 1);
            System.out.println("ОШИБКА: Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        
        try {
            new LinkedListTabulatedFunction(0, 10, new double[]{1});
            System.out.println("ОШИБКА: Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        
        try {
            TabulatedFunction func1 = new ArrayTabulatedFunction(0, 10, 3);
            System.out.println("ArrayTabulatedFunction создан успешно");
            
            TabulatedFunction func2 = new LinkedListTabulatedFunction(0, 10, new double[]{1, 2, 3});
            System.out.println("LinkedListTabulatedFunction создан успешно");
            
        } catch (Exception e) {
            System.out.println("ОШИБКА: " + e.getMessage());
        }
    }
    
    private static void testPointOperations() {
        System.out.println("Тестирование операций с точками:");
        
        TabulatedFunction function = new ArrayTabulatedFunction(0, 4, 3);
        
        System.out.println("Исходная функция:");
        printFunctionPoints(function);
        
        try {
            System.out.println("\nДобавление новой точки:");
            function.addPoint(new FunctionPoint(2.5, 10.0));
            printFunctionPoints(function);
            
            System.out.println("\nДобавление новой точки:");
            function.addPoint(new FunctionPoint(1.5, 5.0));
            printFunctionPoints(function);
            
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при добавлении: " + e.getMessage());
        }
        
        try {
            System.out.println("\nУдаление точки:");
            function.deletePoint(2);
            printFunctionPoints(function);
            
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
        
        try {
            System.out.println("\nИзменение точки:");
            function.setPoint(1, new FunctionPoint(1.8, 8.0));
            printFunctionPoints(function);
            
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при изменении: " + e.getMessage());
        }
    }

    private static void printFunctionPoints(TabulatedFunction function) {
        for (int i = 0; i < function.getPointsCount(); i++) {
            FunctionPoint point = function.getPoint(i);
            System.out.printf("  [%d] (%.2f, %.2f)%n", i, point.getX(), point.getY());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("||| Тестирование TabulatedFunction |||\n");
        
        testTabulatedFunction("ArrayTabulatedFunction", 
            new ArrayTabulatedFunction(0, 10, 5));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testTabulatedFunction("LinkedListTabulatedFunction", 
            new LinkedListTabulatedFunction(0, 10, new double[]{1, 2, 3, 4, 5}));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testConstructorExceptions();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testPointOperations();
    }
    
    
}