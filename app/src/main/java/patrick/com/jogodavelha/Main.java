package patrick.com.jogodavelha;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    //constante da tag de cada botão
    //utilizada para recuperar o botão atraves do metodo getQuadrado
    private final String QUADRADO = "quadrado";

    //contante que vai ser impressa no text do botão
    private final String BOLA     = "O";

    //contante que vai ser impressa no text do botão
    private final String XIS      = "X";

    //guarda ultimo valor jogado
    private String lastPlay       = "X";

    //guarda instancia da view
    private View view;

    //matriz que define as condições para finalizar o jogo
    int[][] statusEnd = new int[][]{
            //verifica linhas
            {1,2,3},//1
            {4,5,6},//2
            {7,8,9},//3
            //verifica colunas
            {1,4,7},//4
            {2,5,8},//5
            {3,6,9},//6
            //verifica diagonais
            {1,5,9},//7
            {3,5,7},//8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //guardar instancia da view
        setView(getLayoutInflater().inflate(R.layout.main, null));
        //passa a instancia da VIEW para a Activity
        setContentView(getView());
    }

    public void clickQuadrado(View v){
        //verifica se o jogo acabou
        if (!isEnd()) {
            //verifica se o texto da variavel v é diferente de vazio
            if (((Button) v).getText().equals("")) {
                if (getLastPlay().equals(XIS)) {//verifica se ultimo vlor jogado é igual a XIS
                    ((Button) v).setText(BOLA);//seta texto do quadrado como O
                    setLastPlay(BOLA);//seta valor de lastPlay como O
                } else {
                    ((Button) v).setText(XIS);//seta texto do quadrado como X
                    setLastPlay(XIS);//seta valor de lastPlay como X
                }
            } else {
                //imprimi com mensagem caso posição já tenha sido jogada
                Toast.makeText(getView().getContext(), "Escolha outro quadrado.", Toast.LENGTH_LONG).show();
            }
            isEnd();//verifica se o jogo acabou
        }
    }

    public Button getQuadrado(int tagNum){
        //retorna o quadrado solicitado pela variavel tagNum
        return (Button)getView().findViewWithTag(QUADRADO+tagNum);
    }

    public void newGame(View v){
        setEnableQuadrado(true);//ativa os quadrados
        setColorDefault();//pinta com a cor Default
        clearFields();//Limpa os quadrados

        RadioButton rX = (RadioButton)getView().findViewById(R.id.rbX);
        //Retorna a instancia do radioButton rbX
        //armazena a instancia na variavel rX
        RadioButton rO = (RadioButton)getView().findViewById(R.id.rbO);
        //radioButton rO

        if (rX.isChecked()){//verifica se rX está marcado;
            setLastPlay( BOLA);//altera lastPlay para o inverso do escolhido
        }else{
            if (rO.isChecked()){
                setLastPlay(XIS);//indica qie o ultimo a jogar foi XIS, proximo joga com BOLA
            }
        }
    }

    public void clearFields(){
        for (int i=1; i<=9; i++){//percorre todos os quadrados
            if (getQuadrado(i)!=null){//verifica se o botão recuperado é diferente de nulo
                getQuadrado(i).setText("");//seta o txto como vazio, limpa campo
            }
        }
    }

    public void setEnableQuadrado(boolean b){
        for (int i=1; i<=9; i++){//percorre todos os quadrados
            if (getQuadrado(i)!=null){//verifica se quadrado é diferente de nulo
                getQuadrado(i).setEnabled(b);//passa o valor de b para o respectivo quadrado
            }
        }
    }

    public void setColorQuadrados(int btn, int colorX){
        //recebe o numero do quadrado pela variavel btn
        //passa a instancia da cor pela variavel colorX
        getQuadrado(btn).setTextColor(ContextCompat.getColor(this, colorX));
        //recupera o quadrado e seta o textColor com a cor passada pela variavel colorX
    }

    public void setColorDefault(){
        for (int i=0; i<=9; ++i){//percorre todos os quadrados
            if (getQuadrado(i)!=null){//verifica se quadrado é diferente de nulo
                setColorQuadrados(i, R.color.black);//envia a cor preta para o metodo setColorQuadrados
            }
        }
    }

    public boolean isEnd(){
        //percorre todas as condições da matriz
        for (int x=0; x<=7; ++x){
            String s1 = getQuadrado(statusEnd[x][0]).getText().toString();
            String s2 = getQuadrado(statusEnd[x][1]).getText().toString();
            String s3 = getQuadrado(statusEnd[x][2]).getText().toString();

            //verifica se s1, s2, s3 são diferentes de vazio
            if ( (!s1.equals(""))&&(!s2.equals(""))&&(!s3.equals("")) ){
                //verifica se s1, s2 e s3 são iguais
                if (s1.equals(s2)&&(s2.equals(s3)) ){
                    //muda a cor do texto do quadrados
                    setColorQuadrados(statusEnd[x][0], R.color.red);
                    setColorQuadrados(statusEnd[x][1], R.color.red);
                    setColorQuadrados(statusEnd[x][2], R.color.red);

                    //indica que o jogo acabou
                    Toast.makeText(getView().getContext(),"Fim de jogo", Toast.LENGTH_LONG).show();

                    return true;//retorna verdadeiro, indicando fim de jogo
                }
            }
        }
        return false;//retorna falso, indicando que o jogo não acabou
    }

    public String getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(String lastPlay) {
        this.lastPlay = lastPlay;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
