package war.model;

import javafx.scene.paint.Color;

public enum State
{
    WANDER
            {
                public Color getColor()
                {
                    return Color.BLACK;
                }
            },
    ATTACK
            {
                public Color getColor()
                {
                    return Color.RED;
                }
            },
    RETURN
            {
                public Color getColor()
                {
                    return Color.GREEN;
                }
            },
    RECOVERING
            {
                public Color getColor()
                {
                    return Color.RED;
                }
            },
    RESOURCE_NEARBY
            {
                public Color getColor()
                {
                    return Color.GREEN;
                }
            },
    ENEMY_NEARBY
            {
                public Color getColor()
                {
                    return Color.RED;
                }
            },
    ENEMY_QUEEN_NEARBY
            {
                public Color getColor()
                {
                    return Color.RED;
                }
            };

    public abstract Color getColor();
}
