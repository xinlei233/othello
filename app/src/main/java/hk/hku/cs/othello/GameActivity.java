package hk.hku.cs.othello;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


public class GameActivity extends Activity implements View.OnClickListener {
    Button btn_Rst;
    ImageButton board[] = new ImageButton[64];
    int rID[] = new int[64];
    ImageView nxt;
    TextView blk_count, wht_count, turn;
    ToggleButton toggle;
    int black_count = 2;
    int white_count = 2;
    boolean nextColor = true;
    int stt[] = new int[64];
    int nboard[][] = new int[8][8];
    private final int[][] adjacentFields = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        nxt = (ImageView) findViewById(R.id.imageView3);
        blk_count = (TextView) findViewById(R.id.textView3);
        wht_count = (TextView) findViewById(R.id.textView4);
        turn = (TextView) findViewById(R.id.textView5);
        //represent text view of black and white count results.
        stt[27] = -1;
        stt[36] = -1;
        stt[28] = 1;
        stt[35] = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                nboard[i][j] = stt[j * 8 + i];
                //System.out.println("nboard"+i+j+"="+nboard[i][j]);
            }
        }
        btn_Rst = (Button) findViewById(R.id.button2);
        btn_Rst.setOnClickListener(this);
        //Listen on Reset button.
        //board[0] = (ImageButton) findViewById(R.id.imageButton);
        //rID[0] = R.id.imageButton;
        //board[0].setOnClickListener(this);
        //Listen on (0,0)
        for (int i = 0; i < board.length; i++) {
            {
                String buttonID = "imageButton" + (i + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //System.out.println("resID[" + i + "] = " + resID);
                rID[i] = resID;
                board[i] = ((ImageButton) findViewById(resID));
                board[i].setOnClickListener(this);
            }
        }
        //Listen on all other imageButtons
        //from http://www.technotalkative.com/android-findviewbyid-in-a-loop/
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                   ShowHints(nextColor);
                    }
                 else {
                    Refresh();
                    // The toggle is disabled

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void ShowHints(boolean v){
        if(v == true){
            for (Position c: getPossibleMoves(v)){
                if(nboard[c.x][c.y] == 0) {
                    showtblack(c);
                }
            }
        }
        if(v == false){
            for(Position c: getPossibleMoves(v)){
                if(nboard[c.x][c.y] == 0) {
                    showtwhite(c);
                }
            }
        }
    }

    public void showtblack(Position pos){
        String buttonID = "imageButton" + (pos.x + 8* pos.y +1);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        ImageButton ib =(ImageButton)findViewById(resID);
        ib.setImageResource(R.drawable.black_chess_t);
    }

    public void showtwhite(Position pos){
        String buttonID = "imageButton" + (pos.x + 8* pos.y +1);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        ImageButton ib =(ImageButton)findViewById(resID);
        ib.setImageResource(R.drawable.white_chess_t);
    }

    public void showblack(Position pos){
        String buttonID = "imageButton" + (pos.x + 8* pos.y +1);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        ImageButton ib =(ImageButton)findViewById(resID);
        ib.setImageResource(R.drawable.black_chess);
    }

    public void showwhite(Position pos){
        String buttonID = "imageButton" + (pos.x + 8* pos.y +1);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        ImageButton ib =(ImageButton)findViewById(resID);
        ib.setImageResource(R.drawable.white_chess);
    }

    public void showtrans(Position pos){
        String buttonID = "imageButton" + (pos.x + 8* pos.y +1);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        ImageButton ib =(ImageButton)findViewById(resID);
        ib.setImageResource(R.drawable.transparent);
    }

    public void Refresh(){
        /*
        for (Position c: getPossibleMoves(nextColor)){
            if(nboard[c.x][c.y] == 0) {
                showtrans(c);
            }
        }
        for (Position c: getPossibleMoves(!nextColor)){
            if(nboard[c.x][c.y] == 0) {
                showtrans(c);
            }
        }
        */
        for (int i=0; i<8; i++){
            for(int j = 0; j<8; j++){
                Position c = new Position(i,j);
                if(nboard[c.x][c.y] == 0){
                    showtrans(c);
                }
                if(nboard[c.x][c.y] == 1){
                    showblack(c);
                }
                if(nboard[c.x][c.y] == -1){
                    showwhite(c);
                }

            }
        }
    }

    public void Place(int[][] b, Position c) {
        if (nextColor == true) {

            //imagebutton v.setImageResource(R.drawable.black_chess);
            int sw = 0;
            showblack(c);
            nxt.setImageResource(R.drawable.white_chess);
            b[c.x][c.y] = 1;
            //Position pos = new Position(0, 0);
            //System.out.println(c.x + " " + c.y);
            for (int[] direction : adjacentFields) { //for every item in adjacentFields
                int switches = getNrOfIncludedPieces(c, direction[0], direction[1], nextColor);
                //System.out.println("switches: "+ switches+", pos: "+c.x+c.y+"direction: "+ direction[0] + direction[1]);
                if (switches > 0) {
                    sw += switches;
                    switchPieces(nextColor, c, direction[0], direction[1]);
                }
            }

            black_count++;
            black_count+= sw;
            white_count-= sw;
            blk_count.setText(": "+Integer.toString(black_count));
            wht_count.setText(": "+Integer.toString(white_count));

            nextColor = false;
            boolean cs = checkState();
            if(cs  == false){
                displayResults(black_count, white_count);
            }

            if(toggle.isChecked()){
                Refresh();
                ShowHints(nextColor);
            }

        } else {
            //v.setImageResource(R.drawable.white_chess);

            showwhite(c);
            int sw = 0;
            nxt.setImageResource(R.drawable.black_chess);
            b[c.x][c.y] = -1;
            //Position pos = new Position(0, 0);
            //System.out.println(c.x + " " + c.y);
            for (int[] direction : adjacentFields) {
                int switches = getNrOfIncludedPieces(c, direction[0], direction[1], nextColor);
                if (switches > 0) {
                    sw += switches;
                    switchPieces(nextColor, c, direction[0], direction[1]);
                }
            }
            white_count++;
            white_count+= sw;
            black_count-= sw;
            wht_count.setText(": "+Integer.toString(white_count));
            blk_count.setText(": "+Integer.toString(black_count));


            nextColor = true;
            boolean cs = checkState();
            if(cs  == false){
                displayResults(black_count, white_count);
            }
            if(toggle.isChecked()){
                Refresh();
                ShowHints(nextColor);
            }

        }

        System.out.println("cs = "+ checkState()+ "nextColor = "+ nextColor);


    }

    private void displayResults(int a, int b){
        if (a > b){
            turn.setText("  Win: ");
            nxt.setImageResource(R.drawable.black_chess);
        } else if (a < b){
            turn.setText("  Win: ");
            nxt.setImageResource(R.drawable.white_chess);
        } else if (a == b){
            turn.setText("  Tie! ");
            nxt.setImageResource(R.drawable.transparent);
        }
    }

    private void switchPieces(boolean v, Position pos, int xDir, int yDir) {
        for (int tmp = 1; ; tmp++) {
            if (((nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] == 1) && (v == true)) || ((nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] == -1) && (v == false))) {
                break;
            } else if ((nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] == 1) && (v == false)) {
                nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] = -1;
                Position n = new Position(pos.x + tmp* xDir, pos.y + tmp * yDir);
                showwhite(n);
                //c[pos.x + tmp * xDir + 8 * (pos.y + tmp * yDir)].setImageResource(R.drawable.white_chess);
            } else if ((nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] == -1) && (v == true)) {
                nboard[pos.x + tmp * xDir][pos.y + tmp * yDir] = 1;
                //c[pos.x + tmp * xDir + 8 * (pos.y + tmp * yDir)].setImageResource(R.drawable.black_chess);
                Position n = new Position(pos.x + tmp* xDir, pos.y + tmp * yDir);
                showblack(n);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println("nboard" + i + j + " = " + nboard[i][j]);
            }
        }
    }

    public boolean IsPlaceable(Position pos) {
        if (nboard[pos.x][pos.y] != 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkState() {
        if ((!isMovePossible(true))&&(!isMovePossible(false))) {
                System.out.print("(!isMovePossible(black) " +(!isMovePossible(true)) );
                System.out.print("(!isMovePossible(white) " +(!isMovePossible(false)) );

            //no one is going to move
                return false;
            }

        else if((!isMovePossible(false))&&(isMovePossible(true))){
            nextColor = true;
            return true;
        }
        else if((!isMovePossible(true))&&(isMovePossible(false))){
            nextColor = false;
            return true;
        }
        else return true;
    }

    private boolean isMovePossible(boolean v) {
        //System.out.println("possible moves are: " + getPossibleMoves(v).size());
        return (getPossibleMoves(v).size() > 0);
    }

    public List<Position> getPossibleMoves(boolean v) {
        List<Position> possibleMoves = new ArrayList<Position>();
        Position pos;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                pos = new Position(x, y);
                if ((IsPlaceable(pos))&& (isMovePositionValid(pos)) &&  (getNrOfSwitches(pos, v) > 0)) {
                    possibleMoves.add(pos);
                    //System.out.println(pos);
                }
            }
        }
        return possibleMoves;
    }

    private boolean isPositionOnBoard(Position pos) {
        if (pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMovePositionValid(Position pos) {
        boolean isMovePositionValid = false;
        if (!isPositionOnBoard(pos)) {
            return false;
        }

        for (int[] field : adjacentFields) {
            Position tmp = new Position(pos.x + field[0], pos.y + field[1]);
            if (isPositionOnBoard(tmp)) {
                isMovePositionValid = true;
            }
        }
        return isMovePositionValid;
    }

    private int getNrOfSwitches(Position pos, boolean v) {
        int switches = 0;

        for (int[] direction : adjacentFields) {
            switches += getNrOfIncludedPieces(pos, direction[0], direction[1], v);
        }
        return switches;
    }

    private int getNrOfIncludedPieces(Position pos, int xDir, int yDir, boolean v) {
        int switches = 0;
        int opponentCount = 0;

        for (int tmp = 1; (pos.x + tmp * xDir >= 0) && (pos.x + tmp * xDir < 8) && (pos.y + tmp * yDir >= 0) && (pos.y + tmp * yDir < 8); tmp++) {
            int piece = nboard[pos.x + tmp * xDir][pos.y + tmp * yDir];

            if ((piece == 1 && v == true) || (piece == -1 && v == false)) {
                switches += opponentCount;
                opponentCount = 0;
                break;
            } else if ((piece == 1 && v == false) || (piece == -1 && v == true)) {
                opponentCount++;
            } else if (piece == 0) {
                return 0;
            }
        }
        return switches;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button2) {
            //Intent intent = new Intent(this, GameActivity.class);
            //startActivity(intent);
            //Back button will not go to MainActivity so the below method is used.
            finish();
            recreate();
            //from Stackoverflow
        }

        for (int i = 0; i < 64; i++) {
            Position pos = new Position(i % 8, i / 8);
            if ((v.getId() == rID[i]) && (isMovePositionValid(pos)) && (isMovePossible(nextColor))) {
                for (Position b: getPossibleMoves(nextColor)) {
                    //System.out.println("b"+b.x+b.y);
                    if ((b.x == pos.x)&&(b.y == pos.y)) {
                        Place(nboard, pos);
                    }
                }

            }
        }
    }
}
