package bd.edu.seu.fall2018ai.assignment1.algorithm;

import bd.edu.seu.fall2018ai.assignment1.model.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;

/**
 * // TODO make sure you type in your name and ID in the following line. Feel free to rename this class if you want to.
 * @author Your Name: Arnab Chakraborty (2014100000067)
 */
public class MySearchAlgorithmImpl extends SearchAlgorithm {
    @Override
    public List<Action> search(Robot2D source, Destination2D destination, List<Shape2D> obstacleList, double mapWidth, double mapHeight) {
        List<Action> actionList = new ArrayList<>();
        // TODO Add your code here. You can have a look at the RandomSearchAlgorithm to see how the collisions are tested.

        List<Pair<Double,Double>> closedList = new ArrayList<>();
        List<Pair<Double,Double>> openList = new ArrayList<>();
        double dis = sqrt(Math.pow(source.getCenter().getX()-destination.getCenter().getX(), 2) + Math.pow(source.getCenter().getY()-destination.getCenter().getY(), 2));
        final int MAX_ACTIONS = 10000;
        int count=0;

        while(dis>destination.getRadius() && count < MAX_ACTIONS){
            count++;
            openList=findChildren(source,obstacleList,mapHeight,mapWidth);
            closedList.add(new Pair<Double, Double>(source.getCenter().getX(),source.getCenter().getY()));
            try {
                openList.removeAll(closedList);
            }catch (Exception e){

            }

            double min_h=999999;
            double x=0;
            double y=0;
            for(Pair<Double,Double>p:openList){
                double h=sqrt(Math.pow(p.getKey()-destination.getCenter().getX(), 2) + Math.pow(p.getValue()-destination.getCenter().getY(), 2));
                if(h<min_h){
                    min_h=h;
                    x=p.getKey();
                    y=p.getValue();
                }
            }


            if(x>source.getCenter().getX()){
                Action randomAction = Action.values()[0];
                source.applyAction(randomAction);
                actionList.add(randomAction);
            }
            if(x<source.getCenter().getX()){
                Action randomAction = Action.values()[1];
                source.applyAction(randomAction);
                actionList.add(randomAction);

            }
            if(y>source.getCenter().getY()){
                Action randomAction = Action.values()[2];
                source.applyAction(randomAction);
                actionList.add(randomAction);


            }
            if(y<source.getCenter().getY()){
                Action randomAction = Action.values()[3];
                source.applyAction(randomAction);
                actionList.add(randomAction);

            }
            dis = sqrt(Math.pow(source.getCenter().getX()-destination.getCenter().getX(), 2) + Math.pow(source.getCenter().getY()-destination.getCenter().getY(), 2));

        }




        return  actionList;

    }

    List<Pair<Double,Double>> findChildren(Robot2D source, List<Shape2D> obstacleList, double mapHeight, double mapWidth){
        List<Pair<Double,Double>> openList=new ArrayList<>();

        double b=source.getCenter().getY()-1;
        for(int i=0;i<3;i++){
            boolean collids=false;
            double a = source.getCenter().getX()-1;
            for(int j=0;j<3;j++){
                Pair<Double,Double> pair=new Pair<>(a,b);
                if (a - source.getRadius() < 0 ||
                        b - source.getRadius() < 0 ||
                        a + source.getRadius() >= mapWidth ||
                        b + source.getRadius() >= mapHeight)
                    collids = true;
                for (Shape2D shape2D : obstacleList) {

                    if (shape2D instanceof Circle2D) {
                        double dis = sqrt(Math.pow(a-((Circle2D) shape2D).getCenter().getX(), 2) + Math.pow(b-((Circle2D) shape2D).getCenter().getY(), 2));
                        if (dis > source.getRadius() + ((Circle2D) shape2D).getRadius()) {
                        }else{
                            collids=true;
                        }
                    }
                    if (shape2D instanceof Rectangle2D) {
                        Circle2D circle2D=new Circle2D(new Point2D(a,b),source.getRadius());
                        if(shape2D.collidesWith(circle2D)){
                            collids=true;
                        }
                    }
                }

                if(!collids){
                    openList.add(pair);
                }else{
                    collids=false;
                }
                a++;
            }
            b++;
        }

        return openList;
    }
}
