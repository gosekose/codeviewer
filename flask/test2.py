def test2(a, b) :
  return a + b


if __name__ == '__main__':
  a = int(input())
  
  for i in range(a):
    a1 = int(input())
    a2 = int(input())
  
    c = test2(a1, a2)
    print(c)
  