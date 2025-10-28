package functions;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points; 
    private int pointsCount;
    private static final double EPSILON = 1e-10;
    
    private boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница области определения не может быть больше или равна правой");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек не может быть меньше двух");
        }

        this.pointsCount = pointsCount;
        this.points = new FunctionPoint[pointsCount];
        
        double step = (rightX - leftX) / (pointsCount - 1);
       
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, 0.0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница области определения не может быть больше или равна правой");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек не может быть меньше двух");
        }

        this.pointsCount = values.length;
        this.points = new FunctionPoint[pointsCount]; 
        
        double step = (rightX - leftX) / (pointsCount - 1);
        
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        for (int i = 0; i < pointsCount - 1; i++) {
            double x1 = points[i].getX();
            double x2 = points[i + 1].getX();
            
            if (x >= x1 && x <= x2) {
                double y1 = points[i].getY();
                double y2 = points[i + 1].getY();
                
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }  
        return 0;
    }

    public int getPointsCount() {
        return pointsCount;
    }
 
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        return new FunctionPoint(points[index]);
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        
        double newX = point.getX();
        
        if (index == 0) {
            if (newX >= points[1].getX() && !doubleEquals(newX, points[1].getX())) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + newX + " должна быть меньше " + points[1].getX()
                );
            }
        }
        else if (index == pointsCount - 1) {
            if (newX <= points[pointsCount - 2].getX() && !doubleEquals(newX, points[pointsCount - 2].getX())) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + newX + " должна быть больше " + points[pointsCount - 2].getX()
                );
            }
        }
        else {
            if ((newX <= points[index - 1].getX() && !doubleEquals(newX, points[index - 1].getX())) || 
                (newX >= points[index + 1].getX() && !doubleEquals(newX, points[index + 1].getX()))) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + newX + " должна быть в интервале (" + 
                    points[index - 1].getX() + ", " + points[index + 1].getX() + ")"
                );
            }
        }
        
        points[index] = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        return points[index].getX();
    }
    
    public double getPointY(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        return points[index].getY();
    }
    
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        
        if (index == 0) {
            if (x >= points[1].getX()) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + x + " должна быть меньше " + points[1].getX()
                );
            }
        }
        else if (index == pointsCount - 1) {
            if (x <= points[pointsCount - 2].getX()) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + x + " должна быть больше " + points[pointsCount - 2].getX()
                );
            }
        }
        else {
            if (x <= points[index - 1].getX() || x >= points[index + 1].getX()) {
                throw new InappropriateFunctionPointException(
                    "Новая координата x=" + x + " должна быть в интервале (" + 
                    points[index - 1].getX() + ", " + points[index + 1].getX() + ")"
                );
            }
        }
        
        points[index].setX(x);
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        
        points[index].setY(y);
    }

    public void deletePoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException(
                "Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"
            );
        }
        
        if (pointsCount < 3) {
            throw new IllegalStateException("Невозможно удалить точку: количество точек не может быть меньше двух");
        }
        
        System.arraycopy(points, index + 1, points, index, pointsCount - index - 1);
        pointsCount--;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = 0; i < pointsCount; i++) {
            if (doubleEquals(point.getX(), points[i].getX())) {
                throw new InappropriateFunctionPointException(
                    "Точка с x=" + point.getX() + " уже существует"
                );
            }
        }
        
        if (pointsCount == points.length) {
            FunctionPoint[] newPoints = new FunctionPoint[points.length + points.length / 2 + 1];
            System.arraycopy(points, 0, newPoints, 0, pointsCount);
            points = newPoints;
        }

        int insertIndex = 0;
        while (insertIndex < pointsCount && point.getX() > points[insertIndex].getX()) {
            insertIndex++;
        }

        System.arraycopy(points, insertIndex, points, insertIndex + 1, pointsCount - insertIndex);
        
        points[insertIndex] = new FunctionPoint(point);
        pointsCount++;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArrayTabulatedFunction{");
        sb.append("pointsCount=").append(pointsCount);
        sb.append(", points=[");
        for (int i = 0; i < pointsCount; i++) {
            if (i > 0) sb.append(", ");
            sb.append(points[i]);
        }
        sb.append("]}");
        return sb.toString();
    }
}