"use strict";

process.stdin.resume();
process.stdin.setEncoding("utf-8");
let inputString: string = "";
let inputLines: string[] = [];
let currentLine: number = 0;
process.stdin.on("data", function (inputStdin: string): void {
  inputString += inputStdin;
});

process.stdin.on("end", function (): void {
  inputLines = inputString.split("\n");
  main();
});

function readLine(): string {
  return inputLines[currentLine++];
}

class TreeNode {
  data: number;
  left: TreeNode | null;
  right: TreeNode | null;

  constructor(data: number) {
    this.data = data;
    this.left = null;
    this.right = null;
  }
}

function insert(root: TreeNode | null, data: number): TreeNode {
  if (root === null) {
    return new TreeNode(data);
  } else {
    if (data <= root.data) {
      root.left = insert(root.left, data);
    } else {
      root.right = insert(root.right, data);
    }
    return root;
  }
}

function traverse(
  topL: number[],
  topR: number[],
  lpos: number,
  rpos: number,
  node: TreeNode | null
): void {
  if (!node) return;

  if (lpos >= 0 && topL[lpos] === 0) {
    topL[lpos] = node.data;
  }

  traverse(topL, topR, lpos + 1, rpos - 1, node.left);
  traverse(topL, topR, lpos - 1, rpos + 1, node.right);

  if (rpos >= 0) {
    topR[rpos] = node.data;
  }
}

function topView(root: TreeNode): void {
  if (!root) return;

  const left: number[] = Array(501).fill(0);
  const right: number[] = Array(501).fill(0);

  traverse(left, right, -1, 0, root);

  let i = 0;
  while (left[i] > 0) i++;
  for (i--; i >= 0; i--) process.stdout.write(`${left[i]} `);
  for (i = 0; right[i] > 0; i++) process.stdout.write(`${right[i]} `);
}

function main() {
  let root: TreeNode | null = null;

  let t: number = Number(inputLines[0]);
  for (let i = 0; i < inputLines[1].split(" ").length; i++) {
    const data: number = Number(inputLines[1].split(" ")[i]);
    root = insert(root, data);
  }

  topView(root);
}
