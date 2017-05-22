function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta

costTemp = 0;
gradTemp = zeros(size(theta));

for i = 1:m
	cost = (-1*y(i).*log(sigmoid(X(i,:)*theta))-(1-y(i)).*(log(1-sigmoid(X(i,:)*theta))));
	costTemp = costTemp + cost;
	gradTemp = gradTemp + ((sigmoid(X(i,:)*theta) - y(i))*(X(i,:)))';
endfor

costTemp = (1/m) * costTemp;
gradTemp = (1/m) * gradTemp;

thetaTempSum = 0;

for j = 2:length(theta)
	thetaTempSum = thetaTempSum + (theta(j) * theta(j));
	gradTemp(j) = gradTemp(j) + (lambda/m)*theta(j);
endfor

J = costTemp + ((lambda/(2*m)) * thetaTempSum);

grad = gradTemp;
% =============================================================

end