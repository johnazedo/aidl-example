# Binders
-  Binders (independente da abordagem) só transfere objetos de até 1MB do serviço para o cliente. É necessário usar o `SharedMemory` (API 27 e acima) ou `MemoryFile` para enviar dados maiores de 1MB;
- `MemoryFile` permite apenas que seja transferido dados com tamanho de 1MB até 128MB;

# Regras do AIDL

| Ação no arquivo aidl                                               | Permissão          |
|--------------------------------------------------------------------|--------------------|
| Adicionar nova função abaixo das demais                            | :white_check_mark: |
| Remover função                                                     | :x:                |
| Alterar ordem de função                                            | :x:                |
| Modificar retorno de função                                        | :x:                |
| Modificar parametros na função                                     | :x:                |
| Adicionar novo campo nullable ao objeto de retorno da função       | :white_check_mark: |
| Remover campo do objeto de retorno da função                       | :x:                |
| Moficiar ordem de campos em um objeto de retorno da função         | :x:                |
| Moficiar tipo ou nome de campos em um objeto de retorno da função  | :x:                |
| Adicionar novo campo nullable ao objeto no parametro da função     | :white_check_mark: |
| Adicionar novo campo não nullable ao objeto no parametro da função | :x:                |
| Remover campo do objeto no parametro da função                     | :x:                |
| Moficiar ordem de campos em um objeto no parametro da função       | :x:                |
| Moficiar tipo ou nome de campos em um objeto no parametro da função | :x:                |
| Overloading de método                                              | :x:                |


