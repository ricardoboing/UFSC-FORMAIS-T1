# Trabalho Prático I - 2018.1

## 1. Definição:
Elaborar uma aplicação, para manipular AF, GR e ER, que envolva as seguintes
operações/funcionalidades:

1.1. Edição, leitura e gravação de GR e ER.  
1.2. Conversão de ER para AF usando o algoritmo De Simone.  
1.3. Transformação de GR para AF e vice-versa.  
1.4. Determinização e Minimização de AF.  
1.5. Obtenção dos AF’s que representam a intersecção, a diferença e o reverso de LR’s (expressas por GR, ER e AF).  
1.6. Obtenção da GR resultante da união, concatenação e fechamento de GR’s.  
1.7. Reconhecimento de sentenças aceitas por um AF e enumeração das sentenças de tamanho “n” aceitas por ele.  

## 2. Observações:
2.1. Para edição e leitura de GR e ER devem ser usados os padrões vistos em aula.
Exemplos: GR: S->aS|a e ER: a*(b?c|d)*.  
2.2. Apresentar os AF na forma de tabelas de transição.  
2.3. Os símbolos do alfabeto devem ter tamanho 1 e podem ser limitados a letras minúsculas e dígitos.  
2.4. Usar & para representar épsilon.  
2.5. A edição das produções das GR’s deve ser textual (direta); ou seja, não há necessidade de definição explícita de Vn e Vt e o símbolo inicial será primeiro Vn definido;  
2.6. Todos os AF´s envolvidos (resultantes e intermediários) em uma operação/verificação devem ser visualizáveis e reutilizáveis em outras operações/ verificações.  
2.7. Além da correção, serão avaliados aspectos de usabilidade e robustez.  
2.8. O trabalho deverá ser feito em duplas.  
2.9. A linguagem de programação é de livre escolha (porém deve ser dominada pelos 2 membros da equipe).  
2.10. Caso sejam usados algoritmos diferentes dos usados em aula, eles devem ser documentados e exemplificados no relatório.  
2.11. O trabalho deve ser encaminhado por e-mail, até 25/05, em um único arquivo zipado, contendo relatório (descrevendo a implementação e sua utilização), fonte (documentado – métodos/funções comentados e com identificação da equipe), executável e testes. Usar como nome do arquivo o nome dos componentes da equipe (ex. JoaoF_MariaG.zip).  